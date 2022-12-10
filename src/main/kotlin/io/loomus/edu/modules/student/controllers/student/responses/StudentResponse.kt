package io.loomus.edu.modules.student.controllers.student.responses

import io.loomus.edu.extensions.seconds
import io.loomus.edu.modules.student.entities.student.StudentEntity

data class StudentResponse(
    val id: Int,
    val courseId: Int,
    val packageId: Int,
    val firstName: String,
    val lastName: String?,
    val phone: String,
    val isActive: Boolean,
    val lastOpenedAt: Int?,
    val progress: Progress?
) {

    data class Progress(
        val opened: Int,
        val passed: Int,
        val total: Int
    )

    constructor(
        entity: StudentEntity,
        progress: Progress? = null
    ) : this(
        id = entity.id,
        courseId = entity.courseId,
        packageId = entity.packageId,
        firstName = entity.firstName,
        lastName = entity.lastName,
        phone = entity.phone,
        isActive = entity.isActive,
        lastOpenedAt = entity.lastOpenedAt?.seconds,
        progress = progress
    )

}
