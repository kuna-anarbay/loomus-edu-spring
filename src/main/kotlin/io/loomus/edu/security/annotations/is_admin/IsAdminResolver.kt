package io.loomus.edu.security.annotations.is_admin

import io.loomus.edu.config.AppConstants
import io.loomus.edu.security.jwt.TokenResponse
import io.loomus.edu.utils.HttpResponse
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class IsAdminResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(IsAdmin::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): TokenResponse {
        if (webRequest.getHeader("X-Admin-Token") != AppConstants.adminCode)
            throw HttpResponse.forbidden()

        return TokenResponse.admin
    }

}
