package io.loomus.edu.modules.homework.modifiers

import io.loomus.edu.extensions.nullIfNeeded
import io.loomus.edu.modules.course.entities.course_package.CoursePackageEntityRepository
import io.loomus.edu.modules.homework.controllers.homework_submission.requests.CreateHomeworkSubmissionBody
import io.loomus.edu.modules.homework.controllers.homework_submission.requests.ReplyHomeworkSubmissionBody
import io.loomus.edu.modules.homework.controllers.homework_submission.responses.HomeworkSubmissionResponse
import io.loomus.edu.modules.homework.entities.homework_submission.HomeworkSubmissionEntity
import io.loomus.edu.modules.homework.entities.homework_submission.HomeworkSubmissionEntityRepository
import io.loomus.edu.modules.homework.entities.homework_submission_resource.HomeworkSubmissionResourceEntityRepository
import io.loomus.edu.modules.program.modifiers.LessonStudentRelationModifier
import io.loomus.edu.modules.student.entities.student.StudentEntityRepository
import io.loomus.edu.security.annotations.is_staff.Staff
import io.loomus.edu.services.storage.DeleteObjectBody
import io.loomus.edu.services.storage.StorageService
import io.loomus.edu.utils.HttpResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class HomeworkSubmissionModifier(
    private val repository: HomeworkSubmissionEntityRepository,
    private val studentRepository: StudentEntityRepository,
    private val storageService: StorageService,
    private val resourceRepository: HomeworkSubmissionResourceEntityRepository,
    private val lessonStudentRelationModifier: LessonStudentRelationModifier,
    private val packageRepository: CoursePackageEntityRepository
) {

    fun createByCourseId(
        courseId: Int,
        homeworkId: Int,
        studentId: Int,
        body: CreateHomeworkSubmissionBody
    ): HomeworkSubmissionResponse {
        val id = HomeworkSubmissionEntity.Id(studentId = studentId, homeworkId = homeworkId)

        val entity = repository.findByIdOrNull(id)?.let {
            if (it.status != HomeworkSubmissionEntity.Status.DECLINED)
                throw HttpResponse.forbidden()
            it.value = body.value?.nullIfNeeded
            it.triesCount += 1
            it.notes = null
            it.submittedAt = LocalDateTime.now()

            repository.save(it)
        } ?: run {
            repository.save(
                HomeworkSubmissionEntity(
                    id = id,
                    courseId = courseId,
                    triesCount = 1,
                    value = body.value?.nullIfNeeded,
                    submittedAt = LocalDateTime.now()
                )
            )
        }
        val student = studentRepository.findByIdOrNull(studentId)
            ?: throw HttpResponse.notFound()
        val coursePackage = packageRepository.findActiveByCourseIdAndStudentId(courseId, studentId)
            ?: throw HttpResponse.notFound()
        if (!coursePackage.homeworkAvailable)
            throw HttpResponse.forbidden()
        lessonStudentRelationModifier.setHomeworkPassedByCourseIdAndStudentId(
            courseId, studentId, homeworkId
        )

        return HomeworkSubmissionResponse(entity, student, listOf())
    }


    fun replyByCourseIdAndId(
        staff: Staff,
        courseId: Int,
        homeworkId: Int,
        studentId: Int,
        body: ReplyHomeworkSubmissionBody
    ): HttpResponse {
        val id = HomeworkSubmissionEntity.Id(studentId = studentId, homeworkId = homeworkId)
        val entity = repository.findByIdOrNull(id)
            ?: throw HttpResponse.notFound()
        entity.status = HomeworkSubmissionEntity.Status.valueOf(body.status)
        entity.notes = body.notes
        repository.save(entity)

        return HttpResponse.success()
    }


    fun deleteByCourseIdAndId(courseId: Int, homeworkId: Int, studentId: Int): HttpResponse {
        val id = HomeworkSubmissionEntity.Id(studentId = studentId, homeworkId = homeworkId)
        val entity = repository.findByIdOrNull(id)
            ?: throw HttpResponse.notFound()
        repository.delete(entity)

        resourceRepository.findAllByHomeworkIdAndStudentId(homeworkId, studentId).forEach {
            storageService.delete(DeleteObjectBody.default(it.path))
            resourceRepository.delete(it)
        }

        return HttpResponse.success()
    }

}