package io.loomus.edu.modules.program.controllers.module

import io.loomus.edu.modules.program.controllers.module.requests.CreateModuleBody
import io.loomus.edu.modules.program.controllers.module.requests.ReorderModuleBody
import io.loomus.edu.modules.program.modifiers.ModuleModifier
import io.loomus.edu.modules.program.providers.ModuleProvider
import io.loomus.edu.security.annotations.is_member.IsMember
import io.loomus.edu.security.annotations.is_member.Member
import io.loomus.edu.security.annotations.is_staff.IsStaff
import io.loomus.edu.security.annotations.is_staff.Staff
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/course/{courseId}/module")
class ModuleController(
    private val modifier: ModuleModifier,
    private val provider: ModuleProvider
) {

    @GetMapping
    fun findAllByCourseId(
        @IsMember member: Member,
        @PathVariable courseId: String
    ) = provider.findAllByCourseId(member, courseId.toInt())


    @PostMapping
    fun createByCourseId(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @Valid @RequestBody body: CreateModuleBody
    ) = modifier.createByCourseId(courseId.toInt(), body)


    @PutMapping("{moduleId}")
    fun editById(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable moduleId: String,
        @Valid @RequestBody body: CreateModuleBody
    ) = modifier.editByCourseIdAndId(courseId.toInt(), moduleId.toInt(), body)


    @PutMapping("{moduleId}/reorder")
    fun reorderById(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable moduleId: String,
        @Valid @RequestBody body: ReorderModuleBody
    ) = modifier.reorderByCourseIdAndId(courseId.toInt(), moduleId.toInt(), body)


    @DeleteMapping("{moduleId}")
    fun deleteById(
        @IsStaff(Staff.Role.ANY) staff: Staff,
        @PathVariable courseId: String,
        @PathVariable moduleId: String
    ) = modifier.deleteByCourseIdAndId(courseId.toInt(), moduleId.toInt())

}