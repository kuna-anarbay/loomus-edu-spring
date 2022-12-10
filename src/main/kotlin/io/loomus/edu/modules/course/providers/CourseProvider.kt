package io.loomus.edu.modules.course.providers

import io.loomus.edu.modules.course.controllers.course.responses.CourseResponse
import io.loomus.edu.modules.course.controllers.course.responses.MemberCourseResponse
import io.loomus.edu.modules.course.controllers.course.responses.RoleResponse
import io.loomus.edu.modules.course.entities.course.CourseEntityRepository
import io.loomus.edu.modules.course.entities.course_banner.CourseBannerEntityRepository
import io.loomus.edu.modules.course.entities.course_subscription.CourseSubscriptionEntityRepository
import io.loomus.edu.modules.program.entities.lesson.LessonEntityRepository
import io.loomus.edu.modules.program.entities.lesson_student_relation.LessonStudentRelationEntityRepository
import io.loomus.edu.modules.staff.controllers.staff.responses.StaffResponse
import io.loomus.edu.modules.staff.entities.staff.StaffEntityRepository
import io.loomus.edu.modules.student.controllers.student.responses.StudentResponse
import io.loomus.edu.modules.student.entities.student.StudentEntityRepository
import io.loomus.edu.security.annotations.is_member.Member
import io.loomus.edu.security.jwt.TokenResponse
import io.loomus.edu.services.cache.CacheName
import io.loomus.edu.utils.HttpResponse
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class CourseProvider(
    private val repository: CourseEntityRepository,
    private val courseBannerRepository: CourseBannerEntityRepository,
    private val studentRepository: StudentEntityRepository,
    private val lessonStudentRelationRepository: LessonStudentRelationEntityRepository,
    private val lessonRepository: LessonEntityRepository,
    private val staffRepository: StaffEntityRepository,
    private val courseSubscriptionRepository: CourseSubscriptionEntityRepository
) {

    fun findAllByUserId(token: TokenResponse): List<MemberCourseResponse> {
        val staffs = staffRepository.findAllByUserId(token.id)
        val students = studentRepository.findAllByUserId(token.id)
        val studentIds = students.map { it.id }.toSet()
        val packageIds = students.map { it.packageId }.toSet()
        val courseIds = (staffs.map { it.courseId } + students.map { it.courseId }).toSet()
        val courses = repository.findAllActiveByIds(courseIds)
        val subscriptions = courseSubscriptionRepository.findAllById(courseIds)
        val authors = staffRepository.findAllOwnersActiveByCourseIds(courseIds)
            .map { StaffResponse(it) }
        val banners = courseBannerRepository.findAllById(courseIds)
        val studentProgress =
            if (studentIds.isNotEmpty()) lessonStudentRelationRepository.findProgressByStudentIds(studentIds)
            else listOf()
        val lessonsCount =
            if (packageIds.isNotEmpty()) lessonRepository.findLessonsCountByPackageIds(packageIds) else listOf()

        val result = mutableListOf<MemberCourseResponse>()
        staffs.forEach { staff ->
            courses.firstOrNull { it.id == staff.courseId }?.let { course ->
                val courseResponse = CourseResponse(course, banners.firstOrNull { it.courseId == course.id })
                val authorName = authors.firstOrNull { it.courseId == course.id }?.fullName ?: ""
                val subscription = subscriptions.firstOrNull { it.courseId == course.id }
                    ?: throw HttpResponse.notFound()
                val isExpired = subscription.expiresAt < LocalDateTime.now()

                result.add(
                    MemberCourseResponse(
                        course = courseResponse,
                        role = Member.Role.valueOf(staff.role.name),
                        authorName = authorName,
                        progress = null,
                        staff = StaffResponse(staff),
                        student = null,
                        isExpired = isExpired
                    )
                )
            }
        }
        students.forEach { student ->
            courses.firstOrNull { it.id == student.courseId }?.let { course ->
                val courseResponse = CourseResponse(course, banners.firstOrNull { it.courseId == course.id })
                val authorName = authors.firstOrNull { it.courseId == course.id }?.fullName ?: ""
                val progress = MemberCourseResponse.Progress(
                    opened = studentProgress.firstOrNull { it.courseId == course.id }?.opened ?: 0,
                    passed = studentProgress.firstOrNull { it.courseId == course.id }?.passed ?: 0,
                    total = lessonsCount.firstOrNull { it.courseId == course.id }?.count?.toInt() ?: 0
                )
                val subscription = subscriptions.firstOrNull { it.courseId == course.id }
                    ?: throw HttpResponse.notFound()
                val isExpired = subscription.expiresAt < LocalDateTime.now()

                result.add(
                    MemberCourseResponse(
                        course = courseResponse,
                        role = Member.Role.STUDENT,
                        authorName = authorName,
                        progress = progress,
                        staff = null,
                        student = StudentResponse(student),
                        isExpired = isExpired
                    )
                )
            }
        }

        return result
    }


    @Cacheable(value = [CacheName.course], key = "#courseId")
    fun findById(courseId: Int) =
        repository.findActiveById(courseId)?.let { course ->
            CourseResponse(course, courseBannerRepository.findByIdOrNull(course.id))
        } ?: throw HttpResponse.notFound()


    fun findByUsername(username: String) =
        repository.findByUsername(username)?.let { course ->
            CourseResponse(course, courseBannerRepository.findByIdOrNull(course.id))
        } ?: throw HttpResponse.notFound()


    fun findRoleByCourseId(member: Member) =
        RoleResponse(role = member.role)

}