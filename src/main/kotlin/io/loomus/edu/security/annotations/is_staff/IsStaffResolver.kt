package io.loomus.edu.security.annotations.is_staff

import io.loomus.edu.config.AppConstants
import io.loomus.edu.extensions.getPathVariable
import io.loomus.edu.modules.staff.entities.staff.StaffEntity
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

class IsStaffResolver(
    private val jwtService: JwtService,
    private val permissionService: PermissionService
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(IsStaff::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Staff {
        val role = parameter.getParameterAnnotation(IsStaff::class.java)?.role
            ?: StaffEntity.Role.ADMIN
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

        val staffRole = permissionService.findRolesByUserId(token.id)
            .firstOrNull { it.courseId == courseId && it.role.isStaff }
            ?: throw HttpResponse.forbidden(LocaledMessage.localize("exception.course.no-access"))

        if (role == StaffEntity.Role.OWNER) {
            if (staffRole.role.isOwner) {
                return Staff(staffRole)
            } else throw HttpResponse.forbidden()
        }

        if (role == StaffEntity.Role.ADMIN) {
            if (staffRole.role.isAdmin) {
                return Staff(staffRole)
            } else throw HttpResponse.forbidden()
        }

        return Staff(staffRole)
    }

}