package io.loomus.edu.modules.user.controllers.user_avatar

import io.loomus.edu.modules.user.modifiers.UserAvatarModifier
import io.loomus.edu.security.annotations.user_id.UserId
import io.loomus.edu.security.jwt.TokenResponse
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/user/avatar")
class UserAvatarController(
    private val modifier: UserAvatarModifier
) {

    @PutMapping
    fun uploadAvatar(
        @UserId(true) userId: TokenResponse,
        @RequestParam("file") file: MultipartFile
    ) = modifier.saveById(userId.id, file)


    @DeleteMapping
    fun deleteAvatar(
        @UserId(true) userId: TokenResponse
    ) = modifier.deleteById(userId.id)

}