package io.loomus.edu.security.permission

import io.loomus.edu.modules.course.entities.course.CourseEntityRepository
import io.loomus.edu.modules.course.entities.course_subscription.CourseSubscriptionEntityRepository
import io.loomus.edu.modules.homework.entities.homework_resource.HomeworkResourceEntityRepository
import io.loomus.edu.modules.homework.entities.homework_submission_resource.HomeworkSubmissionResourceEntityRepository
import io.loomus.edu.modules.program.entities.lesson_resource.LessonResourceEntityRepository
import io.loomus.edu.modules.program.entities.lesson_video.LessonVideoEntityRepository
import io.loomus.edu.modules.staff.entities.staff.StaffEntityRepository
import io.loomus.edu.modules.student.entities.student.StudentEntityRepository
import io.loomus.edu.modules.user.entities.user.UserEntityRepository
import io.loomus.edu.security.annotations.is_member.Member
import io.loomus.edu.services.cache.CacheName
import io.loomus.edu.utils.HttpResponse

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class PermissionService(
    private val staffRepository: StaffEntityRepository,
    private val studentRepository: StudentEntityRepository,
    private val userRepository: UserEntityRepository,
    private val courseRepository: CourseEntityRepository,
    private val subscriptionRepository: CourseSubscriptionEntityRepository,
    private val lessonResourceRepository: LessonResourceEntityRepository,
    private val lessonVideoRepository: LessonVideoEntityRepository,
    private val homeworkResourceRepository: HomeworkResourceEntityRepository,
    private val homeworkSubmissionResourceRepository: HomeworkSubmissionResourceEntityRepository
) {

    @Cacheable(value = [CacheName.coursePermission], key = "#courseId")
    fun findByCourseId(courseId: Int): CourseInfoCacheItem {
        courseRepository.findActiveById(courseId)
            ?: throw HttpResponse.forbidden()
        val subscription = subscriptionRepository.findByCourseId(courseId)
            ?: throw HttpResponse.forbidden()
        val studentsCount = studentRepository.countAllLearningByCourseId(courseId)
        val videosCount = lessonVideoRepository.countAllByCourseId(courseId)
        val lessonResourceSize = lessonResourceRepository.sumSizeByCourseId(courseId) ?: 0L
        val homeworkResourceSize = homeworkResourceRepository.sumSizeByCourseId(courseId) ?: 0L
        val submissionResourceSize = homeworkSubmissionResourceRepository.sumSizeByCourseId(courseId) ?: 0L
        val resourceSize = lessonResourceSize + homeworkResourceSize + submissionResourceSize

        return CourseInfoCacheItem(
            courseId = courseId,
            expiresAt = subscription.expiresAt,
            studentsCount = studentsCount.toInt(),
            maxStudentsCount = subscription.maxStudentsCount,
            videosCount = videosCount.toInt(),
            maxVideosCount = subscription.maxVideosCount,
            resourceSize = resourceSize,
            maxResourceSize = subscription.maxResourceSize
        )
    }

    @CacheEvict(value = [CacheName.coursePermission], key = "#courseId")
    fun evictInfoByCourseId(courseId: Int) {
    }

    fun canUpload(courseId: Int, size: Long): Boolean {
        val info = findByCourseId(courseId)
        return size + info.resourceSize < info.maxResourceSize
    }

    fun canAddStudent(courseId: Int, count: Int = 1): Boolean {
        val info = findByCourseId(courseId)
        return info.studentsCount + count <= info.maxStudentsCount
    }

    fun canAddVideo(courseId: Int): Boolean {
        val info = findByCourseId(courseId)
        return info.videosCount + 1 <= info.maxVideosCount
    }


    @Cacheable(value = [CacheName.userRoles], key = "#userId")
    fun findRolesByUserId(userId: Int): List<UserRoleCacheItem> {
        val staffRoles = staffRepository.findStaffRolesByUserId(userId)
            .map { staff ->
                UserRoleCacheItem(
                    courseId = staff.courseId,
                    userId = userId,
                    role = Member.Role.valueOf(staff.role.name),
                    memberId = staff.id
                )
            }
        val studentRoles = studentRepository.findStudentRolesByUserId(userId)
            .map { student ->
                UserRoleCacheItem(
                    courseId = student.courseId,
                    userId = userId,
                    role = Member.Role.STUDENT,
                    memberId = student.id
                )
            }

        return staffRoles + studentRoles
    }


    @CacheEvict(value = [CacheName.userRoles], key = "#userId")
    fun evictRoleByUserId(userId: Int) {
    }


    fun evictRoleByPhone(phone: String?) = phone?.let {
        userRepository.findByPhone(it)?.let { user ->
            evictRoleByUserId(user.id)
        }
    }


    fun evictRoleByPhones(phones: Set<String>) =
        userRepository.findAllByPhoneIn(phones).map { user ->
            evictRoleByUserId(user.id)
        }

}