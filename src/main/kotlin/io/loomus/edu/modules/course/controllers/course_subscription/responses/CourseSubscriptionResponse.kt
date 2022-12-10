package io.loomus.edu.modules.course.controllers.course_subscription.responses

import io.loomus.edu.extensions.seconds
import io.loomus.edu.security.permission.CourseInfoCacheItem
import java.io.Serializable

data class CourseSubscriptionResponse(
    val courseId: Int,
    val expiresAt: Int,
    val studentsCount: Int,
    val maxStudentsCount: Int,
    val videosCount: Int,
    val maxVideosCount: Int,
    val resourceSize: Long,
    val maxResourceSize: Long
) : Serializable {

    constructor(entity: CourseInfoCacheItem) : this(
        courseId = entity.courseId,
        expiresAt = entity.expiresAt.seconds,
        studentsCount = entity.studentsCount,
        maxStudentsCount = entity.maxStudentsCount,
        videosCount = entity.videosCount,
        maxVideosCount = entity.maxVideosCount,
        resourceSize = entity.resourceSize,
        maxResourceSize = entity.maxResourceSize
    )

}

