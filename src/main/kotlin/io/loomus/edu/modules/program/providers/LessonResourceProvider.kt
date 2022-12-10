package io.loomus.edu.modules.program.providers

import io.loomus.edu.utils.HttpResponse
import io.loomus.edu.modules.program.controllers.lesson_resource.responses.LessonResourceResponse
import io.loomus.edu.modules.program.entities.lesson.LessonEntity
import io.loomus.edu.modules.program.entities.lesson.LessonEntityRepository
import io.loomus.edu.modules.program.entities.lesson_resource.LessonResourceEntityRepository
import io.loomus.edu.security.annotations.is_member.Member
import io.loomus.edu.services.storage.GetSignedUrlBody
import io.loomus.edu.services.storage.StorageService
import org.springframework.stereotype.Service
import java.net.URL
import javax.transaction.Transactional

@Service
@Transactional
class LessonResourceProvider(
    private val repository: LessonResourceEntityRepository,
    private val storageService: StorageService,
    private val lessonRepository: LessonEntityRepository
) {

    fun findAllByCourseIdAndLessonId(courseId: Int, lessonId: Int) =
        repository.findAllByCourseIdAndLessonId(courseId, lessonId)
            .map { LessonResourceResponse(it) }


    fun findAllByCourseIdAndLessonIds(courseId: Int, lessonId: Set<Int>) =
        repository.findAllByCourseIdAndLessonIds(courseId, lessonId)
            .map { LessonResourceResponse(it) }


    fun generateDownloadUrl(member: Member, courseId: Int, lessonId: Int, resourceId: Int): URL {
        val entity = repository.findByCourseIdAndLessonIdAndId(
            courseId, lessonId, resourceId
        ) ?: throw HttpResponse.notFound()
        if (member.role == Member.Role.STUDENT) {
            lessonRepository.findByCourseIdAndId(courseId, lessonId)?.let { lesson ->
                if (lesson.status != LessonEntity.Status.ACTIVE)
                    throw HttpResponse.forbidden()
            } ?: throw HttpResponse.notFound()
        }

        return storageService.generateDownloadUrl(GetSignedUrlBody.default(entity.path))
    }

}