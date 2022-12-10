package io.loomus.edu.modules.course.modifiers

import io.loomus.edu.extensions.nullIfNeeded
import io.loomus.edu.extensions.phone
import io.loomus.edu.modules.course.controllers.course.requests.CreateCourseBody
import io.loomus.edu.modules.course.controllers.course.requests.EditCourseBody
import io.loomus.edu.modules.course.controllers.course.responses.CourseResponse
import io.loomus.edu.modules.course.controllers.course.responses.MemberCourseResponse
import io.loomus.edu.modules.course.entities.course.CourseEntityRepository
import io.loomus.edu.modules.course.entities.course_subscription.CourseSubscriptionEntityRepository
import io.loomus.edu.modules.staff.controllers.staff.responses.StaffResponse
import io.loomus.edu.modules.staff.entities.staff.StaffEntity
import io.loomus.edu.modules.staff.entities.staff.StaffEntityRepository
import io.loomus.edu.modules.user.entities.user.UserEntityRepository
import io.loomus.edu.security.annotations.is_member.Member
import io.loomus.edu.security.permission.PermissionService
import io.loomus.edu.services.cache.CacheName
import io.loomus.edu.utils.HttpResponse
import io.loomus.edu.utils.Validator
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class CourseModifier(
    private val repository: CourseEntityRepository,
    private val courseSubscriptionRepository: CourseSubscriptionEntityRepository,
    private val userRepository: UserEntityRepository,
    private val staffRepository: StaffEntityRepository,
    private val permissionService: PermissionService
) {

    fun createByCourseId(userId: Int, body: CreateCourseBody): MemberCourseResponse {
        Validator.validatePhone(body.phone)
        Validator.validateUsername(body.username)
        val user = userRepository.findByPhone(body.phone.phone)
            ?: throw HttpResponse.notFound()
        val entity = repository.save(body.toEntity())
        val subscription = courseSubscriptionRepository.save(body.subscription.toEntity(entity.id))
        val staffEntity = staffRepository.save(
            StaffEntity(
                courseId = entity.id,
                firstName = user.firstName,
                lastName = user.lastName,
                phone = user.phone,
                role = StaffEntity.Role.OWNER
            )
        )
        permissionService.evictRoleByUserId(user.id)
        val staff = StaffResponse(staffEntity)
        val course = CourseResponse(entity)
        val isExpired = subscription.expiresAt < LocalDateTime.now()

        return MemberCourseResponse(course, Member.Role.OWNER, staff.fullName, null, staff, null, isExpired)
    }


    @CacheEvict(value = [CacheName.course], key = "#courseId")
    fun editById(courseId: Int, body: EditCourseBody): HttpResponse {
        val entity = repository.findActiveById(courseId)
            ?: throw HttpResponse.notFound()
        entity.name = body.name
        entity.description = body.description?.nullIfNeeded
        repository.save(entity)

        return HttpResponse.success()
    }

}