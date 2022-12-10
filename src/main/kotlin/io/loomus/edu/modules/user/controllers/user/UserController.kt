package io.loomus.edu.modules.user.controllers.user

import io.loomus.edu.modules.user.controllers.user.requests.EditPasswordBody
import io.loomus.edu.modules.user.controllers.user.requests.EditUserBody
import io.loomus.edu.modules.user.modifiers.UserModifier
import io.loomus.edu.modules.user.providers.UserProvider
import io.loomus.edu.security.annotations.user_id.UserId
import io.loomus.edu.security.jwt.TokenResponse
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/user")
class UserController(
    private val modifier: UserModifier,
    private val provider: UserProvider
) {

    /**
     *  Providers
     */
    @GetMapping("/me")
    fun findMyProfile(
        @UserId(required = true) userId: TokenResponse
    ) = provider.findById(userId.id)


    /**
     *  Modifiers
     */
    @PutMapping
    fun editUser(
        @UserId(true) userId: TokenResponse,
        @Valid @RequestBody body: EditUserBody
    ) = modifier.editUser(userId.id, body)


    @PutMapping("password")
    fun editUser(
        @UserId(true) userId: TokenResponse,
        @Valid @RequestBody body: EditPasswordBody
    ) = modifier.editPassword(userId.id, body)


    @DeleteMapping
    fun deleteUser(
        @UserId(true) userId: TokenResponse
    ) = modifier.deleteUser(userId.id)

}