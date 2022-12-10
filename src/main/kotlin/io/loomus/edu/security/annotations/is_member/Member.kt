package io.loomus.edu.security.annotations.is_member

import io.loomus.edu.modules.user.entities.user_session.UserSessionEntity
import io.loomus.edu.security.annotations.is_student.Student
import io.loomus.edu.security.permission.UserRoleCacheItem

data class Member(
    val memberId: Int,
    val platform: UserSessionEntity.Platform,
    val role: Role
) {

    constructor(userRole: UserRoleCacheItem, platform: UserSessionEntity.Platform) : this(
        memberId = userRole.memberId,
        role = userRole.role,
        platform = platform
    )

    val isStudent
        get() = role == Role.STUDENT

    enum class Role {
        OWNER, ADMIN, ASSISTANT, STUDENT, ANY;

        val isOwner: Boolean
            get() {
                return listOf(OWNER).contains(this)
            }

        val isAdmin: Boolean
            get() {
                return listOf(OWNER, ADMIN).contains(this)
            }

        val isStaff: Boolean
            get() {
                return listOf(OWNER, ADMIN, ASSISTANT).contains(this)
            }
    }

    companion object {

        fun student(student: Student) = Member(memberId = student.id, role = Role.STUDENT, platform = student.platform)

    }

}
