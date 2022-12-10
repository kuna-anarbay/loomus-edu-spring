package io.loomus.edu.modules.course.controllers.course.requests

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class EditCourseBody(

    @field: NotNull
    @field: NotBlank
    @field: Size(min = 1, max = 255)
    val name: String,

    val description: String?

)