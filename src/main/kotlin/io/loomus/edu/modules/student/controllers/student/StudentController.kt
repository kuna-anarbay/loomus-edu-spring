package io.loomus.edu.modules.student.controllers.student

import io.loomus.edu.modules.student.controllers.student.requests.CreateStudentBody
import io.loomus.edu.modules.student.controllers.student.requests.EditStudentBody
import io.loomus.edu.modules.student.modifiers.StudentModifier
import io.loomus.edu.modules.student.providers.StudentProvider
import io.loomus.edu.security.annotations.is_staff.IsStaff
import io.loomus.edu.security.annotations.is_staff.Staff
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Max

@Validated
@RestController
@RequestMapping("/v1/course/{courseId}/student")
class StudentController(
    private val modifier: StudentModifier,
    private val provider: StudentProvider
) {

    /**
     * Providers
     */
    @GetMapping
    fun findAllActiveByCourseId(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @RequestParam query: String?,
        @RequestParam packageId: Int?,
        @RequestParam isActive: Boolean?,
        @RequestParam(defaultValue = "id") orderBy: String,
        @RequestParam(defaultValue = "asc") orderDirection: String,
        @RequestParam(defaultValue = "20") @Max(100) size: Int,
        @RequestParam(defaultValue = "0") page: Int
    ) = provider.findAllActiveByCourseId(
        courseId.toInt(),
        size, page,
        query,
        packageId,
        isActive,
        orderBy,
        orderDirection
    )


    /**
     * Modifiers
     */
    @PostMapping
    fun createByCourseId(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @Valid @RequestBody body: CreateStudentBody
    ) = modifier.createByCourseId(staff, courseId.toInt(), body)


    @PostMapping("import")
    fun importByCourseId(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @Valid @RequestBody body: List<CreateStudentBody>
    ) = modifier.importByCourseId(staff, courseId.toInt(), body)


    @PutMapping("/{studentId}")
    fun editByCourseIdAndId(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @PathVariable studentId: String,
        @Valid @RequestBody body: EditStudentBody
    ) = modifier.editByCourseIdAndId(courseId.toInt(), studentId.toInt(), body)


    @DeleteMapping("/{studentId}")
    fun deleteByCourseIdAndId(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @PathVariable studentId: String
    ) = modifier.deleteByCourseIdAndId(courseId.toInt(), studentId.toInt())

}