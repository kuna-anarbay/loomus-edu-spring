package io.loomus.edu.modules.program.controllers.lesson.requests

import io.loomus.edu.extensions.nullIfNeeded
import io.loomus.edu.modules.program.entities.lesson.LessonEntity
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class CreateLessonBody(

    @field: NotNull
    val moduleId: Int,

    @field: NotNull
    @field: NotBlank
    @field: Size(max = 255)
    val name: String,

    @field: Size(max = 65535)
    val description: String?,

    @field: NotNull
    @field: NotBlank
    @field: Pattern(regexp = "^(DRAFT|VISIBLE|ACTIVE)$")
    val status: String,

    @field: NotNull
    val packageIds: List<Int>

) {

    fun toEntity(courseId: Int, index: Int) = LessonEntity(
        courseId = courseId,
        moduleId = moduleId,
        name = name,
        description = description?.nullIfNeeded,
        index = index,
        status = LessonEntity.Status.valueOf(status)
    )

}