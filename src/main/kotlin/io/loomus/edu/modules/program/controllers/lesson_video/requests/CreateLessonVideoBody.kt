package io.loomus.edu.modules.program.controllers.lesson_video.requests

import io.loomus.edu.modules.program.entities.lesson_video.LessonVideoEntity
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class CreateLessonVideoBody(

    @field: NotNull
    @field: NotBlank
    val embedUrl: String,

    @field: NotNull
    @field: NotBlank
    @field: Pattern(regexp = "^(YOUTUBE|VIMEO)$")
    val type: String

) {

    fun toEntity(courseId: Int, lessonId: Int) = LessonVideoEntity(
        courseId = courseId,
        lessonId = lessonId,
        embedUrl = embedUrl,
        isActive = true,
        type = LessonVideoEntity.Type.valueOf(type)
    )

}