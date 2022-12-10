package io.loomus.edu.modules.staff.controllers.staff.requests

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class EditStaffBody(

    @field: NotNull
    @field: NotBlank
    @field: Size(min = 1, max = 50)
    val firstName: String,

    val lastName: String?,

    @field: NotNull
    @field: NotBlank
    @field: Size(min = 1, max = 255)
    val phone: String

)