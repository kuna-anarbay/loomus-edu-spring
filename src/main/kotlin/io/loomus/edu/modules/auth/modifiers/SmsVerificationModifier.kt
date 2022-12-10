package io.loomus.edu.modules.auth.modifiers

import io.loomus.edu.config.AppConstants
import io.loomus.edu.modules.auth.entities.sms_verification.SmsVerificationEntity
import io.loomus.edu.modules.auth.entities.sms_verification.SmsVerificationEntityRepository
import io.loomus.edu.services.sms.SmsBody
import io.loomus.edu.services.sms.SmsService
import io.loomus.edu.utils.HttpResponse
import io.loomus.edu.utils.LocaledMessage
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class SmsVerificationModifier(
    private val repository: SmsVerificationEntityRepository,
    private val smsService: SmsService
) {

    fun sendVerificationCode(phone: String): String {
        val code = generateCode()
        val now = LocalDateTime.now()

        repository.findByPhone(phone)?.let {
            val resendAvailable = ChronoUnit.SECONDS.between(it.sendAt, now) >= AppConstants.Auth.resendSmsAvailableIn
            if (!resendAvailable)
                throw HttpResponse.badRequest()
            if (it.codes.split(",").size >= AppConstants.Auth.resendAvailableCount)
                throw HttpResponse.badRequest()

            it.codes += ",$code"
            it.sendAt = now
            repository.save(it)
        } ?: run {
            repository.save(SmsVerificationEntity(phone = phone, codes = code))
        }
        val message = LocaledMessage.localize("message.sms.code").format(code)
        smsService.sendSms(SmsBody(phone, message))

        return code
    }


    fun confirmVerificationCode(phone: String, code: String) {
        repository.findByPhone(phone)?.let {
            if (!it.codes.contains(code)) throw HttpResponse.forbidden()
            repository.delete(it)
        } ?: throw HttpResponse.notFound()
    }


    fun deleteExpired() {
        val expirationTime = LocalDateTime.now().minusMinutes(10)
        repository.deleteAllByExpirationTime(expirationTime)
    }


    private fun generateCode(): String {
        return if (AppConstants.environment == AppConstants.Environment.DEV) {
            "777777"
        } else {
            (Random().nextInt(900000) + 100000).toString()
        }
    }

}