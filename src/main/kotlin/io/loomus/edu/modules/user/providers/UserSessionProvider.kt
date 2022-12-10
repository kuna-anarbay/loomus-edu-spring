package io.loomus.edu.modules.user.providers

import io.loomus.edu.modules.user.controllers.user_session.responses.UserSessionResponse
import io.loomus.edu.modules.user.entities.user_session.UserSessionEntityRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserSessionProvider(
    private val repository: UserSessionEntityRepository
) {

    fun findAllByUserId(userId: Int) =
        repository.findAllByUserId(userId)
            .map { UserSessionResponse(it) }

}