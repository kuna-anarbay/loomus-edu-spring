package io.loomus.edu.security.annotations.user_id

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class UserId(
    val required: Boolean = false
)
