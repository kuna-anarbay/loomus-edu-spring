package io.loomus.edu.modules.course.controllers.course_subscription

import io.loomus.edu.modules.course.modifiers.CourseSubscriptionModifier
import io.loomus.edu.modules.course.providers.CourseSubscriptionProvider
import io.loomus.edu.security.annotations.is_staff.IsStaff
import io.loomus.edu.security.annotations.is_staff.Staff
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/course/{courseId}/subscription")
class CourseSubscriptionController(
    private val modifier: CourseSubscriptionModifier,
    private val provider: CourseSubscriptionProvider
) {

    @GetMapping
    fun findAllByCourseId(
        @IsStaff(Staff.Role.ADMIN) staff: Staff,
        @PathVariable courseId: String
    ) = provider.findByCourseId(courseId.toInt())

}