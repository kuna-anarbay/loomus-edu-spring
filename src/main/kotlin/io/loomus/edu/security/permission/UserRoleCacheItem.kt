package io.loomus.edu.security.permission

import io.loomus.edu.security.annotations.is_member.Member
import java.io.Serializable

data class UserRoleCacheItem(
    val courseId: Int,
    val userId: Int,
    val memberId: Int,
    val role: Member.Role
) : Serializable
