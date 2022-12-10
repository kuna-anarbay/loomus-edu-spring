package io.loomus.edu.modules.course.controllers.course.responses

import io.loomus.edu.modules.staff.controllers.staff.responses.StaffResponse
import io.loomus.edu.modules.student.controllers.student.responses.StudentResponse
import io.loomus.edu.security.annotations.is_member.Member

data class MemberCourseResponse(
    val course: CourseResponse,
    val role: Member.Role,
    val authorName: String,
    val progress: Progress?,
    val staff: StaffResponse?,
    val student: StudentResponse?,
    val isExpired: Boolean
) {

    data class Progress(
        val opened: Int,
        val passed: Int,
        val total: Int
    )
}