package io.loomus.edu.modules.user.controllers.user_session

import io.loomus.edu.modules.user.modifiers.UserSessionModifier
import io.loomus.edu.modules.user.providers.UserSessionProvider
import io.loomus.edu.security.annotations.user_id.UserId
import io.loomus.edu.security.jwt.TokenResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/user/session")
class UserSessionController(
    private val provider: UserSessionProvider,
    private val modifier: UserSessionModifier
) {

    @GetMapping
    fun findAllByUserId(
        @UserId(true) userId: TokenResponse
    ) = provider.findAllByUserId(userId.id)

    @DeleteMapping("{sessionId}")
    fun editDeviceToken(
        @UserId(required = true) userId: TokenResponse,
        @PathVariable sessionId: String
    ) = modifier.deleteByUserIdAndId(userId.id, sessionId.toInt())

}