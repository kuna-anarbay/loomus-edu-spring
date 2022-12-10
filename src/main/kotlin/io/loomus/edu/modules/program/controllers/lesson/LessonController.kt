package io.loomus.edu.modules.program.controllers.lesson

import io.loomus.edu.modules.program.controllers.lesson.requests.CreateLessonBody
import io.loomus.edu.modules.program.controllers.lesson.requests.EditLessonBody
import io.loomus.edu.modules.program.controllers.lesson.requests.ReorderLessonBody
import io.loomus.edu.modules.program.modifiers.LessonModifier
import io.loomus.edu.modules.program.providers.LessonProvider
import io.loomus.edu.security.annotations.is_member.IsMember
import io.loomus.edu.security.annotations.is_member.Member
import io.loomus.edu.security.annotations.is_staff.IsStaff
import io.loomus.edu.security.annotations.is_staff.Staff
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/course/{courseId}/lesson")
class LessonController(
    private val modifier: LessonModifier,
    private val provider: LessonProvider
) {

    @GetMapping("{lessonId}")
    fun findInfoByCourseIdAndId(
        @IsMember member: Member,
        @PathVariable courseId: String,
        @PathVariable lessonId: String,
    ) = provider.findInfoByCourseIdAndId(member, courseId.toInt(), lessonId.toInt())


    @PostMapping
    fun createByCourseId(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @Valid @RequestBody body: CreateLessonBody
    ) = modifier.createByCourseId(courseId.toInt(), body)


    @PutMapping("{lessonId}")
    fun editById(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable lessonId: String,
        @Valid @RequestBody body: EditLessonBody
    ) = modifier.editByCourseIdAndId(courseId.toInt(), lessonId.toInt(), body)

    @PutMapping("{lessonId}/reorder")
    fun reorderById(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable lessonId: String,
        @Valid @RequestBody body: ReorderLessonBody
    ) = modifier.reorderByCourseIdAndId(courseId.toInt(), lessonId.toInt(), body)


    @DeleteMapping("{lessonId}")
    fun deleteById(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable lessonId: String
    ) = modifier.deleteByCourseIdAndId(courseId.toInt(), lessonId.toInt())

}