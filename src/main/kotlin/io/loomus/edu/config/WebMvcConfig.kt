package io.loomus.edu.config

import io.loomus.edu.security.annotations.is_admin.IsAdminResolver
import io.loomus.edu.security.annotations.is_member.IsMemberResolver
import io.loomus.edu.security.annotations.is_staff.IsStaffResolver
import io.loomus.edu.security.annotations.is_student.IsStudentResolver
import io.loomus.edu.security.annotations.user_id.UserIdResolver
import io.loomus.edu.security.jwt.JwtService
import io.loomus.edu.security.permission.PermissionService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.client.RestTemplate
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableWebMvc
@EnableAsync
@EnableScheduling
class WebMvcConfig(
    private val jwtService: JwtService,
    private val permissionService: PermissionService
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(UserIdResolver(jwtService))
        resolvers.add(IsAdminResolver())
        resolvers.add(IsStaffResolver(jwtService, permissionService))
        resolvers.add(IsStudentResolver(jwtService, permissionService))
        resolvers.add(IsMemberResolver(jwtService, permissionService))
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(if (AppConstants.environment == AppConstants.Environment.DEV) "http://localhost:3000" else "https://edu.loomus.io")
            .allowedMethods("*")
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/")

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")
    }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

}