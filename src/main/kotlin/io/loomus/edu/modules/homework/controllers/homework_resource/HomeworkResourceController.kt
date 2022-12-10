package io.loomus.edu.modules.homework.controllers.homework_resource

import io.loomus.edu.modules.homework.modifiers.HomeworkResourceModifier
import io.loomus.edu.modules.homework.providers.HomeworkResourceProvider
import io.loomus.edu.security.annotations.is_member.IsMember
import io.loomus.edu.security.annotations.is_member.Member
import io.loomus.edu.security.annotations.is_staff.IsStaff
import io.loomus.edu.security.annotations.is_staff.Staff
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/course/{courseId}/homework/{homeworkId}/resource")
class HomeworkResourceController(
    private val modifier: HomeworkResourceModifier,
    private val provider: HomeworkResourceProvider
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
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable homeworkId: String,
        @Valid @RequestParam("size") size: Long,
        @Valid @RequestParam("extension") extension: String,
        @Valid @RequestParam("name") name: String,
    ) = modifier.generateUploadUrl(courseId.toInt(), homeworkId.toInt(), size, extension, name)


    @PutMapping("{resourceId}/confirm")
    fun confirmUploadedFile(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable homeworkId: String,
        @PathVariable resourceId: String
    ) = modifier.confirmUploadedFile(courseId.toInt(), homeworkId.toInt(), resourceId.toInt())


    @DeleteMapping("{resourceId}")
    fun createByCourseIdAndHomeworkId(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable homeworkId: String,
        @PathVariable resourceId: String
    ) = modifier.deleteByCourseIdAndId(courseId.toInt(), resourceId.toInt())

}