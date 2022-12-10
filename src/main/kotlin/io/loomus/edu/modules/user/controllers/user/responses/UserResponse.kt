package io.loomus.edu.modules.user.controllers.user.responses

import io.loomus.edu.modules.user.entities.user.UserEntity
import io.loomus.edu.modules.user.entities.user_avatar.UserAvatarEntity
import io.loomus.edu.utils.Gender
import io.loomus.edu.utils.Language
import java.io.Serializable

data class UserResponse(
    val id: Int,
    val phone: String,
    val firstName: String,
    val lastName: String?,
    val birthday: Int?,
    val gender: Gender?,
    val language: Language,
    var avatarUrl: String? = null,
    val status: UserEntity.Status
) : Serializable {

    constructor(user: UserEntity, avatar: UserAvatarEntity?) : this(
        id = user.id,
        phone = user.phone,
        firstName = user.firstName,
        lastName = user.lastName,
        birthday = user.birthday,
        gender = user.gender,
        language = user.language,
        avatarUrl = avatar?.path,
        status = user.status
    )

}
