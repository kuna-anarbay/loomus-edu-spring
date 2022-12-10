package io.loomus.edu.modules.program.controllers.lesson.requests

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class EditLessonBody(

    @field: NotNull
    @field: NotBlank
    @field: Size(max = 255)
    val name: String,

    @field: Size(max = 65535)
    val description: String?,

    @field: NotNull
    @field: NotBlank
    @field: Pattern(regexp = "^(DRAFT|VISIBLE|ACTIVE)$")
    val status: String

)