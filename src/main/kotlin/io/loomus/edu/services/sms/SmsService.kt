package io.loomus.edu.services.sms

import io.loomus.edu.config.AppConstants
import io.loomus.edu.utils.HttpResponse
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.io.*
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder
import javax.transaction.Transactional

@Service
@Transactional
class SmsService {

    companion object {
        const val smscCharset = "utf-8" // кодировка сообщения: koi8-r, windows-1251 или utf-8 (по умолчанию)
    }

    @Async
    fun sendSms(body: SmsBody) {
        if (AppConstants.environment == AppConstants.Environment.DEV) return
        smsSendCmd(
            "cost=3&phones=" + URLEncoder.encode(body.receiver, smscCharset)
                    + "&mes=" + URLEncoder.encode(body.message, smscCharset)
                    + "&translit=0&id=0"
        )
    }


    private fun smsSendCmd(arg: String) {
        var ret: String
        try {
            val encodedLogin = URLEncoder.encode(AppConstants.SMSC.login, smscCharset)
            val encodedPassword = URLEncoder.encode(AppConstants.SMSC.password, smscCharset)

            val defaultUrl =
                "https://smsc.kz/sys/send.php?login=$encodedLogin&psw=$encodedPassword&fmt=1&charset=$smscCharset&$arg"
            var url = defaultUrl
            var i = 0
            do {
                if (i++ > 0) {
                    url = defaultUrl
                    url = url.replace("://smsc.kz/", "://www$i.smsc.kz/")
                }
                ret = smscReadUrl(url)
            } while (ret == "" && i < 5)
        } catch (e: UnsupportedEncodingException) {
            throw HttpResponse.badRequest("Указанная кодировка символов не поддерживается!\n$e\n")
        }
    }


    private fun smscReadUrl(url: String): String {
        var line = ""
        var realUrl = url
        var param = arrayOf<String>()
        val isPost = url.length > 2000
        if (isPost) {
            param = url.split("\\?").toTypedArray()
            realUrl = param[0]
        }
        try {
            val u = URL(realUrl)
            val inputStream: InputStream
            if (isPost) {
                val conn = u.openConnection()
                conn.doOutput = true
                val os = OutputStreamWriter(conn.getOutputStream(), smscCharset)
                os.write(param[1])
                os.flush()
                os.close()
                inputStream = conn.getInputStream()
            } else {
                inputStream = u.openStream()
            }
            val reader = InputStreamReader(inputStream, smscCharset)
            var ch: Int
            while (reader.read().also { ch = it } != -1) {
                line += ch.toChar()
            }
            reader.close()
        } catch (e: MalformedURLException) {
            throw HttpResponse.badRequest("Ошибка при обработке URL-адреса!\n$e\n")
        } catch (e: IOException) {
            throw HttpResponse.badRequest("Ошибка при операции передачи/приема данных!\n$e\n")
        }
        return line
    }

}