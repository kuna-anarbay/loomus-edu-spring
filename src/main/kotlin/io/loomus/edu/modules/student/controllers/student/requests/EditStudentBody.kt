package io.loomus.edu.modules.student.controllers.student.requests

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class EditStudentBody(

    @field: NotNull
    val packageId: Int,

    @field: NotNull
    @field: NotBlank
    @field: Size(min = 1, max = 50)
    val firstName: String,

    val lastName: String?,

    val isActive: Boolean

)