package io.loomus.edu.modules.course.providers

import io.loomus.edu.modules.course.controllers.course_subscription.responses.CourseSubscriptionResponse
import io.loomus.edu.modules.course.entities.course_subscription.CourseSubscriptionEntityRepository
import io.loomus.edu.security.permission.PermissionService
import io.loomus.edu.utils.HttpResponse
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class CourseSubscriptionProvider(
    private val repository: CourseSubscriptionEntityRepository,
    private val permissionService: PermissionService
) {

    fun findByCourseId(courseId: Int) = CourseSubscriptionResponse(permissionService.findByCourseId(courseId))

}