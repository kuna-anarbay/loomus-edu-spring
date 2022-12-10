package io.loomus.edu.modules.program.controllers.lesson_package_relation

import io.loomus.edu.modules.program.modifiers.LessonPackageRelationModifier
import io.loomus.edu.modules.program.providers.LessonPackageRelationProvider
import io.loomus.edu.security.annotations.is_staff.IsStaff
import io.loomus.edu.security.annotations.is_staff.Staff
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/course/{courseId}/package/{packageId}/lesson")
class LessonPackageRelationController(
    private val modifier: LessonPackageRelationModifier,
    private val provider: LessonPackageRelationProvider
) {

    @GetMapping
    fun findAllByCourseId(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @PathVariable packageId: String
    ) = provider.findAllByCourseIdAndPackageId(courseId.toInt(), packageId.toInt())


    @PostMapping("{lessonId}")
    fun createByCourseIdAndPackageId(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @PathVariable packageId: String,
        @PathVariable lessonId: String
    ) = modifier.createByCourseIdAndPackageId(courseId.toInt(), packageId.toInt(), lessonId.toInt())


    @DeleteMapping("{lessonId}")
    fun deleteByCourseIdAndPackageId(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @PathVariable packageId: String,
        @PathVariable lessonId: String
    ) = modifier.deleteByCourseIdAndPackageId(courseId.toInt(), packageId.toInt(), lessonId.toInt())

}