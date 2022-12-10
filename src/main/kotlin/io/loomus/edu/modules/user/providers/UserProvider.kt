package io.loomus.edu.modules.user.providers

import io.loomus.edu.modules.user.controllers.user.responses.UserResponse
import io.loomus.edu.modules.user.entities.user.UserEntity
import io.loomus.edu.modules.user.entities.user.UserEntityRepository
import io.loomus.edu.utils.HttpResponse
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserProvider(
    private val repository: UserEntityRepository,
    private val userAvatarProvider: UserAvatarProvider
) {

    fun findById(userId: Int): UserResponse {
        val user = repository.findByIdAndStatus(userId, UserEntity.Status.ACTIVE) ?: throw HttpResponse.notFound()
        val avatar = userAvatarProvider.findByIdOrNull(userId)

        return UserResponse(user = user, avatar = avatar)
    }

}