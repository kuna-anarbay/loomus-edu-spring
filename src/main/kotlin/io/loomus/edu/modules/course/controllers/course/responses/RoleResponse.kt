package io.loomus.edu.modules.course.controllers.course.responses

import io.loomus.edu.security.annotations.is_member.Member

data class RoleResponse(
    val role: Member.Role
)
