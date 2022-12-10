package io.loomus.edu.modules.auth.controllers.auth.requests

import io.loomus.edu.extensions.phone
import io.loomus.edu.modules.user.entities.user.UserEntity
import io.loomus.edu.modules.user.entities.user_session.UserSessionEntity
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class SignUpBody(

    @field: NotBlank
    val phone: String,

    @field: NotBlank
    @field: Length(min = 2, max = 50)
    val firstName: String,

    @field: Length(max = 50)
    val lastName: String? = null,

    @field: NotBlank
    @field: Size(min = 8, max = 50)
    val password: String,

    @field: NotBlank
    @field: Length(min = 6, max = 6)
    val code: String,

    @field: NotBlank
    @field: Pattern(regexp = "^(DESKTOP|PHONE)$")
    val platform: String,

    @field: NotBlank
    @field: Length(max = 255)
    val deviceType: String,

    @field: NotBlank
    @field: Length(max = 255)
    val os: String,

    @field: NotBlank
    @field: Length(max = 255)
    val version: String

) : SessionCreate {

    fun toUser(): UserEntity {
        return UserEntity(phone = phone.phone, firstName = firstName, lastName = lastName, password = password)
    }

    override fun toSession(userId: Int, ipAddress: String?) =
        UserSessionEntity(
            userId = userId,
            ipAddress = ipAddress ?: "unknown",
            os = os,
            deviceType = deviceType,
            version = version,
            platform = UserSessionEntity.Platform.valueOf(platform)
        )
}
