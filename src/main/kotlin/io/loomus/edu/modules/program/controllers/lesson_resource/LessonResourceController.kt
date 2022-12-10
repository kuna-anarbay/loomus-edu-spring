package io.loomus.edu.modules.program.controllers.lesson_resource

import io.loomus.edu.modules.program.modifiers.LessonResourceModifier
import io.loomus.edu.modules.program.providers.LessonResourceProvider
import io.loomus.edu.security.annotations.is_member.IsMember
import io.loomus.edu.security.annotations.is_member.Member
import io.loomus.edu.security.annotations.is_staff.IsStaff
import io.loomus.edu.security.annotations.is_staff.Staff
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/course/{courseId}/lesson/{lessonId}/resource")
class LessonResourceController(
    private val modifier: LessonResourceModifier,
    private val provider: LessonResourceProvider
) {

    @GetMapping("{resourceId}/download-url")
    fun generateDownloadUrl(
        @IsMember member: Member,
        @PathVariable courseId: String,
        @PathVariable lessonId: String,
        @PathVariable resourceId: String
    ) = provider.generateDownloadUrl(
        member,
        courseId.toInt(),
        lessonId.toInt(),
        resourceId.toInt()
    )

    @PostMapping("upload-url")
    fun createByCourseIdAndLessonId(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable lessonId: String,
        @Valid @RequestParam("size") size: Long,
        @Valid @RequestParam("extension") extension: String,
        @Valid @RequestParam("name") name: String,
    ) = modifier.generateUploadUrl(courseId.toInt(), lessonId.toInt(), size, extension, name)


    @PutMapping("{resourceId}/confirm")
    fun confirmUploadedFile(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable lessonId: String,
        @PathVariable resourceId: String
    ) = modifier.confirmUploadedFile(courseId.toInt(), lessonId.toInt(), resourceId.toInt())


    @DeleteMapping("{resourceId}")
    fun createByCourseIdAndLessonId(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable lessonId: String,
        @PathVariable resourceId: String
    ) = modifier.deleteByCourseIdAndId(courseId.toInt(), resourceId.toInt())

}