package io.loomus.edu.modules.auth.controllers.auth.requests

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class SendSmsVerificationCodeBody(

    @field: NotBlank
    val phone: String,

    @field: NotBlank
    @field: Pattern(regexp = "^(SIGN_UP|RESET_PASSWORD)$")
    private val purpose: String

) {

    val type: Purpose
        get() = Purpose.valueOf(purpose)

    enum class Purpose {
        SIGN_UP, RESET_PASSWORD
    }

}
