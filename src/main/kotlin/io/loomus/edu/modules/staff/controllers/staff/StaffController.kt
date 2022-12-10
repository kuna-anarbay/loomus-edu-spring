package io.loomus.edu.modules.staff.controllers.staff

import io.loomus.edu.modules.staff.controllers.staff.requests.CreateStaffBody
import io.loomus.edu.modules.staff.controllers.staff.requests.EditStaffBody
import io.loomus.edu.modules.staff.modifiers.StaffModifier
import io.loomus.edu.modules.staff.providers.StaffProvider
import io.loomus.edu.security.annotations.is_staff.IsStaff
import io.loomus.edu.security.annotations.is_staff.Staff
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/course/{courseId}/staff")
class StaffController(
    private val modifier: StaffModifier,
    private val provider: StaffProvider
) {

    @GetMapping
    fun findAllActiveByCourseId(
        @IsStaff staff: Staff,
        @PathVariable courseId: String
    ) = provider.findAllActiveByCourseId(courseId.toInt())


    @PostMapping
    fun createByCourseId(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @Valid @RequestBody body: CreateStaffBody
    ) = modifier.createByCourseId(staff, courseId.toInt(), body)


    @PutMapping("/{staffId}")
    fun editByCourseIdAndId(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @PathVariable staffId: String,
        @Valid @RequestBody body: EditStaffBody
    ) = modifier.editByCourseIdAndId(courseId.toInt(), staffId.toInt(), body)


    @DeleteMapping("/{staffId}")
    fun deleteByCourseIdAndId(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @PathVariable staffId: String
    ) = modifier.deleteByCourseIdAndId(courseId.toInt(), staffId.toInt())

}