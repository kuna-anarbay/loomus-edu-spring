package io.loomus.edu.modules.homework.controllers.homework_submission_resource

import io.loomus.edu.modules.homework.modifiers.HomeworkSubmissionResourceModifier
import io.loomus.edu.modules.homework.providers.HomeworkSubmissionResourceProvider
import io.loomus.edu.security.annotations.is_member.IsMember
import io.loomus.edu.security.annotations.is_member.Member
import io.loomus.edu.security.annotations.is_staff.IsStaff
import io.loomus.edu.security.annotations.is_staff.Staff
import io.loomus.edu.security.annotations.is_student.IsStudent
import io.loomus.edu.security.annotations.is_student.Student
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/course/{courseId}/homework/{homeworkId}/submission/resource")
class HomeworkSubmissionResourceController(
    private val modifier: HomeworkSubmissionResourceModifier,
    private val provider: HomeworkSubmissionResourceProvider
) {

    @GetMapping("{resourceId}/download-url")
    fun generateDownloadUrl(
        @IsMember member: Member,
        @PathVariable courseId: String,
        @PathVariable homeworkId: String,
        @PathVariable resourceId: String
    ) = provider.generateDownloadUrl(member, courseId.toInt(), homeworkId.toInt(), resourceId.toInt())


    @PostMapping("upload-url")
    fun createByCourseIdAndHomeworkId(
        @IsStudent student: Student,
        @PathVariable courseId: String,
        @PathVariable homeworkId: String,
        @Valid @RequestParam("size") size: Long,
        @Valid @RequestParam("extension") extension: String,
        @Valid @RequestParam("name") name: String,
    ) = modifier.generateUploadUrl(courseId.toInt(), homeworkId.toInt(), student.id, size, extension, name)


    @PutMapping("{resourceId}/confirm")
    fun confirmUploadedFile(
        @IsStudent student: Student,
        @PathVariable courseId: String,
        @PathVariable homeworkId: String,
        @PathVariable resourceId: String
    ) = modifier.confirmUploadedFile(courseId.toInt(), homeworkId.toInt(), student.id, resourceId.toInt())


    @DeleteMapping("{resourceId}")
    fun createByCourseIdAndHomeworkId(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable homeworkId: String,
        @PathVariable resourceId: String
    ) = modifier.deleteByCourseIdAndId(courseId.toInt(), resourceId.toInt())

}