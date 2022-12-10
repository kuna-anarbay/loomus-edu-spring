package io.loomus.edu.modules.course.controllers.course.requests

import io.loomus.edu.extensions.nullIfNeeded
import io.loomus.edu.modules.course.controllers.course_subscription.requests.CreateCourseSubscriptionBody
import io.loomus.edu.modules.course.entities.course.CourseEntity
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class CreateCourseBody(

    @field: NotNull
    @field: NotBlank
    @field: Size(max = 63)
    val username: String,

    @field: NotNull
    @field: NotBlank
    @field: Size(max = 63)
    val name: String,

    val description: String?,

    @field: NotNull
    @field: NotBlank
    val phone: String,

    @field: NotNull
    val subscription: CreateCourseSubscriptionBody

) {

    fun toEntity() = CourseEntity(
        username = username.trim(),
        name = name,
        description = description?.nullIfNeeded
    )

}