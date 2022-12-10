package io.loomus.edu.security.permission

import java.io.Serializable
import java.time.LocalDateTime

data class CourseInfoCacheItem(
    val courseId: Int,
    val expiresAt: LocalDateTime,
    val studentsCount: Int,
    val maxStudentsCount: Int,
    val videosCount: Int,
    val maxVideosCount: Int,
    val resourceSize: Long,
    val maxResourceSize: Long
) : Serializable
