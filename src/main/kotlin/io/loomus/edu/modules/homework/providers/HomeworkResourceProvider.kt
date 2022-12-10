package io.loomus.edu.modules.homework.providers

import io.loomus.edu.modules.homework.entities.homework_resource.HomeworkResourceEntityRepository
import io.loomus.edu.modules.program.entities.lesson.LessonEntity
import io.loomus.edu.modules.program.entities.lesson.LessonEntityRepository
import io.loomus.edu.security.annotations.is_member.Member
import io.loomus.edu.services.storage.GetSignedUrlBody
import io.loomus.edu.services.storage.StorageService
import io.loomus.edu.utils.HttpResponse
import org.springframework.stereotype.Service
import java.net.URL
import javax.transaction.Transactional

@Service
@Transactional
class HomeworkResourceProvider(
    private val repository: HomeworkResourceEntityRepository,
    private val storageService: StorageService,
    private val lessonRepository: LessonEntityRepository
) {

    fun generateDownloadUrl(member: Member, courseId: Int, homeworkId: Int, resourceId: Int): URL {
        val entity = repository.findByCourseIdAndId(courseId, resourceId)
            ?: throw HttpResponse.notFound()
        if (member.role == Member.Role.STUDENT) {
            lessonRepository.findByCourseIdAndId(courseId, homeworkId)?.let { lesson ->
                if (lesson.status != LessonEntity.Status.ACTIVE)
                    throw HttpResponse.forbidden()
            } ?: throw HttpResponse.notFound()
        }

        return storageService.generateDownloadUrl(GetSignedUrlBody.default(entity.path))
    }

}