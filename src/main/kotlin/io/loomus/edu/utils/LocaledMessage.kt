package io.loomus.edu.utils

import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.stereotype.Component
import java.util.*

@Component
class LocaledMessage {
    companion object {
        /**
         *  Translates text
         */
        fun localize(msgCode: String): String {
            val messageSource = ResourceBundleMessageSource()
            messageSource.setBasenames("message")
            messageSource.setDefaultEncoding("UTF-8")
            messageSource.setUseCodeAsDefaultMessage(true)

            return messageSource.getMessage(msgCode, null, Locale("ru"))
        }
    }
}