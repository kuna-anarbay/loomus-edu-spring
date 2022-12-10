package io.loomus.edu.security.jwt

import io.loomus.edu.config.AppConstants
import io.loomus.edu.modules.user.entities.user_session.UserSessionEntity

data class TokenResponse(
    val id: Int,
    val platform: UserSessionEntity.Platform
) {

    companion object {
        val admin = TokenResponse(AppConstants.adminId, UserSessionEntity.Platform.DESKTOP)
    }

}
