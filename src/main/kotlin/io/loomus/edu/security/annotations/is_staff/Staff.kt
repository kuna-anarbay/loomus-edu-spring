package io.loomus.edu.security.annotations.is_staff

import io.loomus.edu.security.annotations.is_member.Member
import io.loomus.edu.security.permission.UserRoleCacheItem
import java.io.Serializable

data class Staff(
    val id: Int,
    val userId: Int,
    val role: Role
) : Serializable {

    val isAdmin: Boolean
        get() = role == Role.ADMIN || role == Role.OWNER

    enum class Role {
        OWNER, ADMIN, ASSISTANT, ANY
    }

    constructor(userRole: UserRoleCacheItem) : this(
        id = userRole.memberId,
        userId = userRole.userId,
        role = when (userRole.role) {
            Member.Role.OWNER -> Role.OWNER
            Member.Role.ADMIN -> Role.ADMIN
            else -> Role.ASSISTANT
        }
    )

}
