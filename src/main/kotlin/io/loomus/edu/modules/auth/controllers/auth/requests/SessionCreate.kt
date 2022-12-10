package io.loomus.edu.modules.auth.controllers.auth.requests

import io.loomus.edu.modules.user.entities.user_session.UserSessionEntity

interface SessionCreate {
    fun toSession(userId: Int, ipAddress: String?): UserSessionEntity
}