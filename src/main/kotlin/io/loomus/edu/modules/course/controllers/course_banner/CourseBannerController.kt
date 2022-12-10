package io.loomus.edu.modules.course.controllers.course_banner

import io.loomus.edu.modules.course.modifiers.CourseBannerModifier
import io.loomus.edu.modules.course.providers.CourseBannerProvider
import io.loomus.edu.security.annotations.is_staff.IsStaff
import io.loomus.edu.security.annotations.is_staff.Staff
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/course/{courseId}/banner")
class CourseBannerController(
    private val modifier: CourseBannerModifier,
    private val provider: CourseBannerProvider
) {

    @PutMapping
    fun uploadAvatar(
        @IsStaff staff: Staff,
        @RequestParam("file") file: MultipartFile,
        @PathVariable courseId: String
    ) = modifier.saveById(courseId.toInt(), file)


    @DeleteMapping
    fun deleteAvatar(
        @IsStaff staff: Staff,
        @PathVariable courseId: String
    ) = modifier.deleteById(courseId.toInt())

}