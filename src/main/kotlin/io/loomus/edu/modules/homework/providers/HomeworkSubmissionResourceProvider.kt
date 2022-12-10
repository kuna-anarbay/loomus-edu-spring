package io.loomus.edu.modules.homework.providers

import io.loomus.edu.modules.homework.entities.homework_submission.HomeworkSubmissionEntity
import io.loomus.edu.modules.homework.entities.homework_submission.HomeworkSubmissionEntityRepository
import io.loomus.edu.modules.homework.entities.homework_submission_resource.HomeworkSubmissionResourceEntityRepository
import io.loomus.edu.modules.program.entities.lesson.LessonEntity
import io.loomus.edu.modules.program.entities.lesson.LessonEntityRepository
import io.loomus.edu.security.annotations.is_member.Member
import io.loomus.edu.services.storage.GetSignedUrlBody
import io.loomus.edu.services.storage.StorageService
import io.loomus.edu.utils.HttpResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.net.URL
import javax.transaction.Transactional

@Service
@Transactional
class HomeworkSubmissionResourceProvider(
    private val repository: HomeworkSubmissionResourceEntityRepository,
    private val storageService: StorageService,
    private val lessonRepository: LessonEntityRepository,
    private val homeworkSubmissionRepository: HomeworkSubmissionEntityRepository
) {

    fun generateDownloadUrl(member: Member, courseId: Int, homeworkId: Int, resourceId: Int): URL {
        val entity = repository.findByCourseIdAndId(courseId, resourceId)
            ?: throw HttpResponse.notFound()
        val submission = homeworkSubmissionRepository.findByIdOrNull(
            HomeworkSubmissionEntity.Id(
                homeworkId = entity.homeworkId, studentId = entity.studentId
            )
        ) ?: throw HttpResponse.notFound()
        if (member.role == Member.Role.STUDENT) {
            lessonRepository.findByCourseIdAndId(courseId, homeworkId)?.let { lesson ->
                if (lesson.status != LessonEntity.Status.ACTIVE)
                    throw HttpResponse.forbidden()
            } ?: throw HttpResponse.notFound()
            if (submission.id.studentId != member.memberId)
                throw HttpResponse.forbidden()
        }

        return storageService.generateDownloadUrl(GetSignedUrlBody.default(entity.path))
    }

}