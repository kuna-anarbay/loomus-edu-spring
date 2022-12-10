package io.loomus.edu.modules.user.controllers.user.requests

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class EditUserBody(

    @field: NotBlank
    @field: Size(min = 2, max = 50)
    val firstName: String,

    @field: Size(max = 50)
    val lastName: String?,

    val birthday: Int?,

    val gender: String?,

    @field: NotNull
    @field: NotBlank
    @field: Pattern(regexp = "^(KK|RU|EN)$")
    val language: String

)
