package io.loomus.edu.modules.course.controllers.course

import io.loomus.edu.modules.course.controllers.course.requests.CreateCourseBody
import io.loomus.edu.modules.course.controllers.course.requests.EditCourseBody
import io.loomus.edu.modules.course.modifiers.CourseModifier
import io.loomus.edu.modules.course.providers.CourseProvider
import io.loomus.edu.modules.student.modifiers.StudentModifier
import io.loomus.edu.security.annotations.is_admin.IsAdmin
import io.loomus.edu.security.annotations.is_member.IsMember
import io.loomus.edu.security.annotations.is_member.Member
import io.loomus.edu.security.annotations.is_staff.IsStaff
import io.loomus.edu.security.annotations.is_staff.Staff
import io.loomus.edu.security.annotations.is_student.IsStudent
import io.loomus.edu.security.annotations.is_student.Student
import io.loomus.edu.security.annotations.user_id.UserId
import io.loomus.edu.security.jwt.TokenResponse
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/course")
class CourseController(
    private val modifier: CourseModifier,
    private val provider: CourseProvider,
    private val studentModifier: StudentModifier
) {

    @GetMapping
    fun findAllByCourseId(
        @UserId(true) userId: TokenResponse
    ) = provider.findAllByUserId(userId)


    @GetMapping("{courseId}")
    fun findByCourseIdAndId(
        @PathVariable courseId: String
    ) = provider.findById(courseId.toInt())


    @GetMapping("username/{username}")
    fun findByUsername(
        @PathVariable username: String
    ) = provider.findByUsername(username)


    @GetMapping("{courseId}/role")
    fun findRoleByCourseId(
        @IsMember member: Member,
        @PathVariable courseId: String
    ) = provider.findRoleByCourseId(member)


    @PostMapping
    fun createByCourseId(
        @IsAdmin userId: TokenResponse,
        @Valid @RequestBody body: CreateCourseBody
    ) = modifier.createByCourseId(userId.id, body)


    @PutMapping("{courseId}/opened")
    fun editLastOpenedAt(
        @IsStudent student: Student,
        @PathVariable courseId: String
    ) = studentModifier.editLastOpenedAt(courseId.toInt(), student.id)


    @PutMapping("{courseId}")
    fun editById(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @Valid @RequestBody body: EditCourseBody
    ) = modifier.editById(courseId.toInt(), body)

}