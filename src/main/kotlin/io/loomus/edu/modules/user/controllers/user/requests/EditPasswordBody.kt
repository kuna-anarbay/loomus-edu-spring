package io.loomus.edu.modules.user.controllers.user.requests

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class EditPasswordBody(

    @field: NotBlank
    @field: Size(min = 8, max = 50)
    val oldPassword: String,

    @field: NotBlank
    @field: Size(min = 8, max = 50)
    val newPassword: String

)
