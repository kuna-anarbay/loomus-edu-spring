package io.loomus.edu.modules.auth.controllers.auth

import io.loomus.edu.modules.auth.controllers.auth.requests.*
import io.loomus.edu.modules.auth.modifiers.AuthModifier
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val modifier: AuthModifier
) {

    @PostMapping("verification-code")
    fun sendEmailVerificationCode(
        @Valid @RequestBody body: SendSmsVerificationCodeBody
    ) = modifier.sendEmailVerificationCode(body)


    @PostMapping("sign-up")
    fun signUp(
        @Valid @RequestBody body: SignUpBody,
        @RequestHeader("X-Forwarded-For", required = false) ipAddress: String?
    ) = modifier.signUp(body, ipAddress)


    @PostMapping("sign-in")
    fun signIn(
        @Valid @RequestBody body: SignInBody,
        @RequestHeader("X-Forwarded-For", required = false) ipAddress: String?
    ) = modifier.signIn(body, ipAddress)


    @PostMapping("reset-password")
    fun resetPassword(
        @Valid @RequestBody body: ResetPasswordBody,
        @RequestHeader("X-Forwarded-For", required = false) ipAddress: String?
    ) = modifier.resetPassword(body, ipAddress)


    @PostMapping("refresh-token")
    fun refreshToken(
        @Valid @RequestBody body: RefreshTokenBody,
        @RequestHeader("X-Forwarded-For", required = false) ipAddress: String?
    ) = modifier.refreshToken(body, ipAddress)


    @PostMapping("sign-out")
    fun signOut(
        @Valid @RequestBody body: SignOutBody
    ) = modifier.signOut(body)

}