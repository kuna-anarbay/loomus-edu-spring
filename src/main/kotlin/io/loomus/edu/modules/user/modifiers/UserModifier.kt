package io.loomus.edu.modules.user.modifiers

import io.loomus.edu.extensions.editById
import io.loomus.edu.extensions.nullIfNeeded
import io.loomus.edu.modules.user.controllers.user.requests.EditPasswordBody
import io.loomus.edu.modules.user.controllers.user.requests.EditUserBody
import io.loomus.edu.modules.user.entities.user.UserEntity
import io.loomus.edu.modules.user.entities.user.UserEntityRepository
import io.loomus.edu.utils.Gender
import io.loomus.edu.utils.HttpResponse
import io.loomus.edu.utils.Language
import io.loomus.edu.utils.LocaledMessage
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserModifier(
    private val repository: UserEntityRepository,
    private val sessionModifier: UserSessionModifier
) {

    fun editUser(userId: Int, body: EditUserBody): HttpResponse {
        repository.editById(userId) { user ->
            user.firstName = body.firstName
            user.lastName = body.lastName?.nullIfNeeded
            user.birthday = body.birthday
            user.gender = body.gender?.let { Gender.valueOf(it) }
            user.language = Language.valueOf(body.language)
        }

        return HttpResponse.success()
    }


    fun editPassword(userId: Int, body: EditPasswordBody): HttpResponse {
        repository.editById(userId) { user ->
            if (body.oldPassword != user.password) throw HttpResponse.badRequest(LocaledMessage.localize("exception.user.wrong-password"))
            user.password = body.newPassword
        }

        return HttpResponse.success()
    }


    fun deleteUser(userId: Int): HttpResponse {
        repository.editById(userId) {
            it.status = UserEntity.Status.DELETED
        }
        sessionModifier.deleteAllByUserId(userId)

        return HttpResponse.success()
    }

}