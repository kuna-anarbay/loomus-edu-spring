package io.loomus.edu.security.annotations.user_id

import io.loomus.edu.config.AppConstants
import io.loomus.edu.security.jwt.JwtService
import io.loomus.edu.security.jwt.TokenResponse
import io.loomus.edu.utils.HttpResponse
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class UserIdResolver(
    private val jwtService: JwtService
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(UserId::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): TokenResponse? {
        val adminToken = webRequest.getHeader("X-Admin-Token")
        if (adminToken == AppConstants.adminCode) {
            return TokenResponse.admin
        }
        val required = parameter.getParameterAnnotation(UserId::class.java)?.required ?: false
        return try {
            val userId = jwtService.verifyToken(webRequest.getHeader(AppConstants.Auth.authHeaderName))
            if (required && userId == null) {
                throw HttpResponse.unauthorized()
            }

            return userId
        } catch (e: Exception) {
            if (required) throw HttpResponse.unauthorized()
            null
        }
    }

}
