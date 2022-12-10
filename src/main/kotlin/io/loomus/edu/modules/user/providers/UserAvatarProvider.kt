package io.loomus.edu.modules.user.providers

import io.loomus.edu.modules.user.entities.user_avatar.UserAvatarEntityRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserAvatarProvider(
    private val repository: UserAvatarEntityRepository
) {

    fun findByIdOrNull(userId: Int) =
        repository.findByIdOrNull(userId)

}