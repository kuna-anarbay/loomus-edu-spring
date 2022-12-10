package io.loomus.edu.modules.program.controllers.lesson_video

import io.loomus.edu.modules.program.controllers.lesson_video.requests.CreateLessonVideoBody
import io.loomus.edu.modules.program.modifiers.LessonVideoModifier
import io.loomus.edu.security.annotations.is_staff.IsStaff
import io.loomus.edu.security.annotations.is_staff.Staff
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/course/{courseId}/lesson/{lessonId}/video")
class LessonVideoController(
    private val modifier: LessonVideoModifier
) {

    @PostMapping
    fun createByLessonId(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable lessonId: String,
        @Valid @RequestBody body: CreateLessonVideoBody
    ) = modifier.createByLessonId(courseId.toInt(), lessonId.toInt(), body)


    @PostMapping("upload-url")
    fun createByCourseIdAndLessonId(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable lessonId: String,
        @Valid @RequestParam("size") size: Long
    ) = modifier.generateUploadUrl(courseId.toInt(), lessonId.toInt(), size)


    @PutMapping("confirm")
    fun confirmUploadedFile(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable lessonId: String
    ) = modifier.confirmUploadedFile(courseId.toInt(), lessonId.toInt())


    @DeleteMapping
    fun createByCourseIdAndLessonId(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable lessonId: String
    ) = modifier.deleteByLessonId(courseId.toInt(), lessonId.toInt())

}