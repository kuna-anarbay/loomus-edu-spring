package io.loomus.edu.modules.program.providers

import io.loomus.edu.modules.course.controllers.course_package.responses.PackageResponse
import io.loomus.edu.modules.course.entities.course_package.CoursePackageEntityRepository
import io.loomus.edu.modules.homework.providers.HomeworkProvider
import io.loomus.edu.modules.program.controllers.lesson.responses.LessonDetailsResponse
import io.loomus.edu.modules.program.controllers.lesson.responses.LessonResponse
import io.loomus.edu.modules.program.controllers.lesson_video.responses.LessonVideoResponse
import io.loomus.edu.modules.program.entities.lesson.LessonEntity
import io.loomus.edu.modules.program.entities.lesson.LessonEntityRepository
import io.loomus.edu.modules.program.entities.lesson_video.LessonVideoEntity
import io.loomus.edu.modules.program.entities.lesson_video.LessonVideoEntityRepository
import io.loomus.edu.modules.program.modifiers.LessonStudentRelationModifier
import io.loomus.edu.security.annotations.is_member.Member
import io.loomus.edu.services.vimeo.VimeoService
import io.loomus.edu.utils.HttpResponse
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class LessonProvider(
    private val repository: LessonEntityRepository,
    private val resourceProvider: LessonResourceProvider,
    private val homeworkProvider: HomeworkProvider,
    private val packageRepository: CoursePackageEntityRepository,
    private val lessonStudentRelationModifier: LessonStudentRelationModifier,
    private val lessonVideoRepository: LessonVideoEntityRepository,
    private val vimeoService: VimeoService
) {

    fun findInfoByCourseIdAndId(member: Member, courseId: Int, lessonId: Int): LessonDetailsResponse {
        val lesson = repository.findByCourseIdAndId(courseId, lessonId)
            ?: throw HttpResponse.notFound()
        val resources = resourceProvider.findAllByCourseIdAndLessonId(courseId, lessonId)
        if (member.role == Member.Role.STUDENT) {
            if (lesson.status != LessonEntity.Status.ACTIVE)
                throw HttpResponse.forbidden()
        }
        val coursePackage = if (member.role == Member.Role.STUDENT)
            packageRepository.findActiveByCourseIdAndStudentId(courseId, member.memberId)
        else null
        val studentLessonRelation = if (member.role === Member.Role.STUDENT)
            lessonStudentRelationModifier.setLessonPassedByCourseIdAndStudentId(
                courseId, member.memberId, lessonId
            ) else null
        val packages = if (member.role != Member.Role.STUDENT)
            packageRepository.findAllActiveByCourseIdAndLessonId(courseId, lessonId)
                .map { PackageResponse(it) } else listOf()
        val video = lessonVideoRepository.findByCourseIdAndLessonId(courseId, lessonId)?.let {
            val status =
                if (it.type === LessonVideoEntity.Type.UPLOAD) vimeoService.getStatus(it.videoId)?.upload?.status
                else null
            LessonVideoResponse(it, status ?: "complete")
        }
        val lessonResponse =
            LessonResponse(
                lesson, video, resources, studentLessonRelation?.homeworkPassed ?: false,
                packages = if (member.isStudent) listOf() else packages
            )
        val homeworkAvailable = coursePackage?.homeworkAvailable ?: true
        val homework = if (homeworkAvailable)
            homeworkProvider.findAllActiveByCourseIdAndIdOrNull(member, courseId, lessonId)
        else null

        return LessonDetailsResponse(
            lesson = lessonResponse,
            homework = homework
        )
    }

}