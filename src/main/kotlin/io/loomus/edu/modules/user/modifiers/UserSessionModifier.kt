package io.loomus.edu.modules.user.modifiers

import io.loomus.edu.config.AppConstants
import io.loomus.edu.modules.auth.controllers.auth.requests.RefreshTokenBody
import io.loomus.edu.modules.user.controllers.user_session.responses.UserSessionResponse
import io.loomus.edu.modules.user.entities.user.UserEntity
import io.loomus.edu.modules.user.entities.user_session.UserSessionEntity
import io.loomus.edu.modules.user.entities.user_session.UserSessionEntityRepository
import io.loomus.edu.utils.HttpResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class UserSessionModifier(
    private val repository: UserSessionEntityRepository
) {

    fun createSession(user: UserEntity, entity: UserSessionEntity): UserSessionResponse {
        val sessions = repository.findAllByUserId(entity.userId)
        repository.deleteAll(sessions.filter { it.platform == entity.platform })

        return UserSessionResponse(repository.save(entity))
    }


    fun updateSession(id: Int, body: RefreshTokenBody, ipAddress: String?): UserSessionResponse {
        val session = repository.findByIdOrNull(id) ?: throw HttpResponse.unauthorized()
        session.ipAddress = ipAddress ?: "unknown"
        session.deviceType = body.deviceType
        session.os = body.os
        session.version = body.version
        session.lastActiveAt = LocalDateTime.now()

        return UserSessionResponse(session)
    }


    fun deleteExpired() {
        val expirationTime = LocalDateTime.now().minusMonths(AppConstants.Auth.sessionExpiresMonths.toLong())
        repository.deleteExpired(expirationTime)
    }


    fun deleteAllByUserId(userId: Int) =
        repository.deleteAllByUserId(userId)


    fun deleteSession(id: Int) =
        repository.deleteById(id)


    fun deleteByUserIdAndId(userId: Int, sessionId: Int) {
        val session = repository.findByIdOrNull(sessionId)
            ?: throw HttpResponse.notFound()
        if (session.userId != userId) throw HttpResponse.forbidden()
        repository.delete(session)
    }


}