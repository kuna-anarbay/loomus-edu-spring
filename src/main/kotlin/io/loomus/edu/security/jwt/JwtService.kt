package io.loomus.edu.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.loomus.edu.config.AppConstants
import io.loomus.edu.modules.user.entities.user_session.UserSessionEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService {

    companion object {
        val algorithm: Algorithm = Algorithm.HMAC256(AppConstants.Auth.authPrivateKey)
    }

    fun generateToken(
        subject: String,
        expirationTime: Long,
        platform: UserSessionEntity.Platform
    ): String {
        return JWT.create()
            .withSubject(subject)
            .withIssuer(AppConstants.serverBaseUrl)
            .withAudience(AppConstants.clientBaseUrl)
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + expirationTime * 1000))
            .withClaim(AppConstants.Auth.sourceClaim, platform.name)
            .sign(algorithm)
    }


    fun verifyToken(token: String?): TokenResponse? {
        val accessToken = token?.split(" ")?.lastOrNull() ?: return null
        return try {
            val jwt = JWT.require(algorithm)
                .withIssuer(AppConstants.serverBaseUrl)
                .withAudience(AppConstants.clientBaseUrl)
                .build()
                .verify(accessToken)
            val userId = jwt.subject.toInt()
            val sourceClaim = jwt.getClaim(AppConstants.Auth.sourceClaim).asString().filter { it != '"' }
            val platform = UserSessionEntity.Platform.valueOf(sourceClaim)

            return TokenResponse(userId, platform)
        } catch (e: Exception) {
            null
        }
    }


}