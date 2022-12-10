package io.loomus.edu.modules.student.controllers.student.requests

import io.loomus.edu.extensions.nullIfNeeded
import io.loomus.edu.extensions.phone
import io.loomus.edu.modules.student.entities.student.StudentEntity
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class CreateStudentBody(

    @field: NotNull
    val packageId: Int,

    @field: NotNull
    @field: NotBlank
    @field: Size(min = 1, max = 50)
    val firstName: String,

    val lastName: String?,

    @field: NotNull
    @field: NotBlank
    @field: Size(min = 1, max = 31)
    val phone: String,

    @field: NotNull
    val isActive: Boolean

) {

    fun toEntity(courseId: Int) = StudentEntity(
        courseId = courseId,
        packageId = packageId,
        firstName = firstName.trim(),
        lastName = lastName?.trim()?.nullIfNeeded,
        phone = phone.phone,
        isActive = isActive,
    )

}