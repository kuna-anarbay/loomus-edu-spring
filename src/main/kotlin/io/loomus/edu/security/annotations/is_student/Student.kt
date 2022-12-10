package io.loomus.edu.security.annotations.is_student

import io.loomus.edu.modules.user.entities.user_session.UserSessionEntity
import io.loomus.edu.security.permission.UserRoleCacheItem
import java.io.Serializable

data class Student(
    val id: Int,
    val courseId: Int,
    val platform: UserSessionEntity.Platform,
    val userId: Int
) : Serializable {

    constructor(userRole: UserRoleCacheItem, platform: UserSessionEntity.Platform) : this(
        id = userRole.memberId,
        courseId = userRole.courseId,
        userId = userRole.userId,
        platform = platform
    )

}
