package io.loomus.edu.modules.auth.controllers.auth.requests

import javax.validation.constraints.NotBlank

data class SignOutBody(

    @field: NotBlank
    val refreshToken: String

)
