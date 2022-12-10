package io.loomus.edu.modules.staff.controllers.staff.responses

import io.loomus.edu.modules.staff.entities.staff.StaffEntity

data class StaffResponse(
    val id: Int,
    val courseId: Int,
    val firstName: String,
    val lastName: String?,
    val phone: String,
    val role: StaffEntity.Role
) {

    val fullName: String
        get() = firstName + lastName?.let { " $it" }

    constructor(entity: StaffEntity) : this(
        id = entity.id,
        courseId = entity.courseId,
        firstName = entity.firstName,
        lastName = entity.lastName,
        phone = entity.phone,
        role = entity.role
    )

}
