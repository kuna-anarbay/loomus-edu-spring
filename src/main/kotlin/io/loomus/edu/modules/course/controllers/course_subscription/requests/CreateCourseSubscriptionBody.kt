package io.loomus.edu.modules.course.controllers.course_subscription.requests

import io.loomus.edu.extensions.dateTime
import io.loomus.edu.modules.course.entities.course_subscription.CourseSubscriptionEntity
import javax.validation.constraints.NotNull

class CreateCourseSubscriptionBody(

    @field: NotNull
    val maxStudentsCount: Int,

    @field: NotNull
    val maxVideosCount: Int,

    @field: NotNull
    val maxResourceSize: Long,

    @field: NotNull
    val expiresAt: Int

) {

    fun toEntity(courseId: Int) = CourseSubscriptionEntity(
        courseId = courseId,
        maxStudentsCount = maxStudentsCount,
        maxVideosCount = maxVideosCount,
        maxResourceSize = maxResourceSize,
        expiresAt = expiresAt.dateTime
    )

}