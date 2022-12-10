package io.loomus.edu.modules.homework.controllers.homework

import io.loomus.edu.modules.homework.controllers.homework.requests.CreateHomeworkBody
import io.loomus.edu.modules.homework.modifiers.HomeworkModifier
import io.loomus.edu.modules.homework.providers.HomeworkProvider
import io.loomus.edu.security.annotations.is_member.IsMember
import io.loomus.edu.security.annotations.is_member.Member
import io.loomus.edu.security.annotations.is_staff.IsStaff
import io.loomus.edu.security.annotations.is_staff.Staff
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/course/{courseId}/lesson/{lessonId}/homework")
class HomeworkController(
    private val modifier: HomeworkModifier,
    private val provider: HomeworkProvider
) {

    @GetMapping
    fun findActiveByCourseIdAndId(
        @IsMember member: Member,
        @PathVariable courseId: String,
        @PathVariable lessonId: String
    ) = provider.findAllActiveByCourseIdAndIdOrNull(member, courseId.toInt(), lessonId.toInt())


    @PostMapping
    fun createByCourseId(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable lessonId: String,
        @Valid @RequestBody body: CreateHomeworkBody
    ) = modifier.createByCourseId(courseId.toInt(), lessonId.toInt(), body)


    @DeleteMapping
    fun deleteByCourseIdAndId(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable lessonId: String
    ) = modifier.deleteByCourseIdAndId(courseId.toInt(), lessonId.toInt())

}