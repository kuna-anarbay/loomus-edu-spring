package io.loomus.edu.modules.auth.modifiers

import io.loomus.edu.config.AppConstants
import io.loomus.edu.extensions.phone
import io.loomus.edu.modules.auth.controllers.auth.requests.*
import io.loomus.edu.modules.auth.controllers.auth.requests.SendSmsVerificationCodeBody.Purpose
import io.loomus.edu.modules.auth.controllers.auth.responses.AuthResponse
import io.loomus.edu.modules.user.entities.user.UserEntity
import io.loomus.edu.modules.user.entities.user.UserEntityRepository
import io.loomus.edu.modules.user.entities.user_session.UserSessionEntity
import io.loomus.edu.modules.user.modifiers.UserSessionModifier
import io.loomus.edu.security.jwt.JwtService
import io.loomus.edu.utils.HttpResponse
import io.loomus.edu.utils.LocaledMessage
import io.loomus.edu.utils.Validator
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class AuthModifier(
    private val smsVerificationModifier: SmsVerificationModifier,
    private val sessionModifier: UserSessionModifier,
    private val userRepository: UserEntityRepository,
    private val jwtService: JwtService
) {


    fun sendEmailVerificationCode(body: SendSmsVerificationCodeBody): HttpResponse {
        Validator.validatePhone(body.phone)
        if (body.type == Purpose.RESET_PASSWORD) {
            val user = userRepository.findByPhone(body.phone.phone)
                ?: throw HttpResponse.notFound(LocaledMessage.localize("exception.user.not-found"))
            if (user.status == UserEntity.Status.BLOCKED)
                throw HttpResponse.notFound(LocaledMessage.localize("exception.user.blocked"))
        } else {
            if (userRepository.findByPhone(body.phone.phone) != null)
                throw HttpResponse.notFound(LocaledMessage.localize("exception.user.already-exists"))
        }
        smsVerificationModifier.sendVerificationCode(body.phone.phone)

        return HttpResponse.success()
    }


    fun signUp(body: SignUpBody, ipAddress: String?): AuthResponse {
        Validator.validatePhone(body.phone)
        if (userRepository.findByPhone(body.phone.phone) != null)
            throw HttpResponse.badRequest(LocaledMessage.localize("exception.user.already-exists"))
        smsVerificationModifier.confirmVerificationCode(body.phone.phone, body.code)
        val user = userRepository.save(body.toUser())

        return authenticate(user, body, ipAddress)
    }


    fun signIn(body: SignInBody, ipAddress: String?): AuthResponse {
        Validator.validatePhone(body.phone)
        val user = userRepository.findByPhone(body.phone.phone)
            ?: throw HttpResponse.notFound(LocaledMessage.localize("exception.user.not-found"))
        if (user.status == UserEntity.Status.BLOCKED)
            throw HttpResponse.forbidden(LocaledMessage.localize("exception.user.blocked"))
        if (body.password != user.password)
            throw HttpResponse.badRequest(LocaledMessage.localize("exception.auth.wrong-password"))
        user.status = UserEntity.Status.ACTIVE
        userRepository.save(user)

        return authenticate(user, body, ipAddress)
    }


    fun resetPassword(body: ResetPasswordBody, ipAddress: String?): AuthResponse {
        Validator.validatePhone(body.phone)
        smsVerificationModifier.confirmVerificationCode(body.phone.phone, body.code)
        val user = userRepository.findByPhone(body.phone.phone)
            ?: throw HttpResponse.notFound()
        if (user.status == UserEntity.Status.BLOCKED)
            throw HttpResponse.forbidden(LocaledMessage.localize("exception.user.blocked"))
        user.password = body.password
        userRepository.save(user)

        return authenticate(user, body, ipAddress)
    }


    fun refreshToken(body: RefreshTokenBody, ipAddress: String?): AuthResponse {
        val sessionId = jwtService.verifyToken(body.refreshToken)?.id
            ?: throw HttpResponse.unauthorized()
        val sessionDto = sessionModifier.updateSession(sessionId, body, ipAddress)
        val user = userRepository.findByIdAndStatus(sessionDto.userId, UserEntity.Status.ACTIVE)
            ?: throw HttpResponse.notFound()

        return generateAuthResponse(user.id, sessionDto.id, sessionDto.platform)
    }


    fun signOut(body: SignOutBody) {
        val sessionId = jwtService.verifyToken(body.refreshToken)?.id
            ?: throw HttpResponse.unauthorized()
        sessionModifier.deleteSession(sessionId)
    }


    private fun authenticate(
        user: UserEntity,
        body: SessionCreate,
        ipAddress: String?
    ): AuthResponse {
        val session = body.toSession(user.id, ipAddress)
        val sessionDto = sessionModifier.createSession(user, session)

        return generateAuthResponse(user.id, sessionDto.id, sessionDto.platform)
    }


    private fun generateAuthResponse(
        userId: Int,
        sessionId: Int,
        platform: UserSessionEntity.Platform
    ): AuthResponse {
        val accessToken = jwtService.generateToken(
            userId.toString(),
            AppConstants.Auth.accessTokenExpiresIn,
            platform
        )
        val refreshToken = jwtService.generateToken(
            sessionId.toString(),
            AppConstants.Auth.refreshTokenExpiresIn,
            platform
        )

        return AuthResponse(AppConstants.Auth.accessTokenExpiresIn.toInt(), accessToken, refreshToken)
    }

}