package io.loomus.edu.utils

import com.google.i18n.phonenumbers.PhoneNumberUtil
import io.loomus.edu.extensions.phone

object Validator {

    fun validatePhone(phone: String) {
        val phoneNumberUtil = PhoneNumberUtil.getInstance()
        try {
            val numberProto = phoneNumberUtil.parse(phone.phone, "KZ")
            if (!phoneNumberUtil.isValidNumber(numberProto))
                throw HttpResponse.badRequest(LocaledMessage.localize("exception.invalid-phone-number"))
        } catch (exception: Exception) {
            throw HttpResponse.badRequest(LocaledMessage.localize("exception.invalid-phone-number"))
        }
    }

    fun validateUsername(username: String) {
        val usernameRegex = "^[\\w](?!.*?\\.{2})[\\w.]{1,28}[\\w]\$".toRegex()
        if (!usernameRegex.matches(username))
            throw HttpResponse.badRequest(LocaledMessage.localize("exception.course.invalid-username"))
    }

}