package io.loomus.edu.modules.course.controllers.course_package

import io.loomus.edu.modules.course.controllers.course_package.requests.CreatePackageBody
import io.loomus.edu.modules.course.controllers.course_package.requests.EditPackageBody
import io.loomus.edu.modules.course.modifiers.CoursePackageModifier
import io.loomus.edu.modules.course.providers.CoursePackageProvider
import io.loomus.edu.security.annotations.is_staff.IsStaff
import io.loomus.edu.security.annotations.is_staff.Staff
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/course/{courseId}/package")
class CoursePackageController(
    private val modifier: CoursePackageModifier,
    private val provider: CoursePackageProvider
) {

    @GetMapping
    fun findAllByCourseId(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
    ) = provider.findAllByCourseId(courseId.toInt())


    @PostMapping
    fun createByCourseId(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @Valid @RequestBody body: CreatePackageBody
    ) = modifier.createByCourseId(courseId.toInt(), body)


    @PutMapping("{packageId}")
    fun editById(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @PathVariable packageId: String,
        @Valid @RequestBody body: EditPackageBody
    ) = modifier.editByCourseIdAndId(courseId.toInt(), packageId.toInt(), body)


    @DeleteMapping("{packageId}")
    fun editById(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @PathVariable packageId: String
    ) = modifier.deleteByCourseIdAndId(courseId.toInt(), packageId.toInt())

}