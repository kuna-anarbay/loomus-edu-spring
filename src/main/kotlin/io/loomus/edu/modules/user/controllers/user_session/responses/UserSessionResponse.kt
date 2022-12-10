package io.loomus.edu.modules.user.controllers.user_session.responses

import io.loomus.edu.extensions.seconds
import io.loomus.edu.modules.user.entities.user_session.UserSessionEntity
import java.io.Serializable

data class UserSessionResponse(
    val id: Int,
    val userId: Int,
    val platform: UserSessionEntity.Platform,
    val ipAddress: String,
    val deviceType: String,
    val os: String,
    val version: String,
    val lastActiveAt: Int,
    val createdAt: Int
) : Serializable {

    constructor(session: UserSessionEntity) : this(
        id = session.id,
        userId = session.userId,
        platform = session.platform,
        ipAddress = session.ipAddress,
        deviceType = session.deviceType,
        os = session.os,
        version = session.version,
        lastActiveAt = session.lastActiveAt.seconds,
        createdAt = session.createdAt.seconds
    )
}
