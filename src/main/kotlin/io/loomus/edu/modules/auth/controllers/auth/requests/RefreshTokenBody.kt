package io.loomus.edu.modules.auth.controllers.auth.requests

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class RefreshTokenBody(

    @field: NotBlank
    val refreshToken: String,

    @field: NotBlank
    @field: Size(max = 255)
    val os: String,

    @field: Length(max = 255)
    val deviceType: String,

    @field: NotBlank
    @field: Size(max = 255)
    val version: String

)
