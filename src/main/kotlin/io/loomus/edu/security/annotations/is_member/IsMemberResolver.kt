package io.loomus.edu.security.annotations.is_member

import io.loomus.edu.config.AppConstants
import io.loomus.edu.extensions.getPathVariable
import io.loomus.edu.security.jwt.JwtService
import io.loomus.edu.security.jwt.TokenResponse
import io.loomus.edu.security.permission.PermissionService
import io.loomus.edu.utils.HttpResponse
import io.loomus.edu.utils.LocaledMessage
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.time.LocalDateTime

class IsMemberResolver(
    private val jwtService: JwtService,
    private val permissionService: PermissionService
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(IsMember::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Member {
        val adminToken = webRequest.getHeader("X-Admin-Token")
        val token = if (adminToken == AppConstants.adminCode) TokenResponse.admin else {
            jwtService.verifyToken(webRequest.getHeader(AppConstants.Auth.authHeaderName))
                ?: throw HttpResponse.unauthorized()
        }
        val courseId = webRequest.getPathVariable("courseId")
            ?: throw HttpResponse.forbidden()

        val info = permissionService.findByCourseId(courseId)
        if (info.expiresAt < LocalDateTime.now())
            throw HttpResponse.forbidden(LocaledMessage.localize("exception.course-subscription.expired"))

        val courseRoles = permissionService.findRolesByUserId(token.id)
            .filter { it.courseId == courseId }

        courseRoles.firstOrNull { it.role.isAdmin }?.let {
            return Member(it, token.platform)
        }

        courseRoles.firstOrNull { it.role.isStaff }?.let {
            return Member(it, token.platform)
        }

        courseRoles.firstOrNull { it.role == Member.Role.STUDENT }?.let {
            return Member(it, token.platform)
        }

        throw HttpResponse.forbidden(LocaledMessage.localize("exception.course.no-access"))
    }

}