package io.loomus.edu.modules.auth.controllers.auth.requests

import io.loomus.edu.modules.user.entities.user_session.UserSessionEntity
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class ResetPasswordBody(

    @field: NotBlank
    val phone: String,

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
    @field: Size(max = 255)
    val deviceType: String,

    @field: NotBlank
    @field: Size(max = 255)
    val os: String,

    @field: NotBlank
    @field: Size(max = 255)
    val version: String

) : SessionCreate {

    override fun toSession(userId: Int, ipAddress: String?) =
        UserSessionEntity(
            userId = userId,
            ipAddress = ipAddress ?: "unknown",
            os = os,
            version = version,
            platform = UserSessionEntity.Platform.valueOf(platform),
            deviceType = deviceType
        )

}

