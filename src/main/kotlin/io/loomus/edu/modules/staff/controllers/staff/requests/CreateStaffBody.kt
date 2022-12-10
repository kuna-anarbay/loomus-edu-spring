package io.loomus.edu.modules.staff.controllers.staff.requests

import io.loomus.edu.extensions.nullIfNeeded
import io.loomus.edu.extensions.phone
import io.loomus.edu.modules.staff.entities.staff.StaffEntity
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class CreateStaffBody(

    @field: NotNull
    @field: NotBlank
    @field: Size(min = 1, max = 50)
    val firstName: String,

    val lastName: String?,

    @field: NotNull
    @field: NotBlank
    @field: Size(min = 1, max = 255)
    val phone: String,

    @field: NotNull
    @field: NotBlank
    @field: Pattern(regexp = "^(ADMIN|ASSISTANT)$")
    val role: String

) {

    fun toEntity(courseId: Int) = StaffEntity(
        courseId = courseId,
        firstName = firstName,
        lastName = lastName?.nullIfNeeded,
        phone = phone.phone,
        role = StaffEntity.Role.valueOf(role)
    )

}