package io.loomus.edu.security.annotations.is_staff

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class IsStaff(
    val role: Staff.Role = Staff.Role.ANY
)
