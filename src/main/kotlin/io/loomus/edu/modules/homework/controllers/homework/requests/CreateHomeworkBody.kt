package io.loomus.edu.modules.homework.controllers.homework.requests

import io.loomus.edu.extensions.dateTime
import io.loomus.edu.modules.homework.entities.homework.HomeworkEntity
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class CreateHomeworkBody(

    @field: NotNull
    @field: NotBlank
    val value: String,

    val deadlineAt: Int?

) {

    fun toEntity(courseId: Int, lessonId: Int) = HomeworkEntity(
        courseId = courseId,
        id = lessonId,
        value = value,
        deadlineAt = deadlineAt?.dateTime,
        isDeleted = false
    )

}