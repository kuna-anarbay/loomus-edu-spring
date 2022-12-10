package io.loomus.edu.modules.program.modifiers

import io.loomus.edu.modules.program.controllers.lesson_video.requests.CreateLessonVideoBody
import io.loomus.edu.modules.program.controllers.lesson_video.responses.LessonVideoResponse
import io.loomus.edu.modules.program.controllers.lesson_video.responses.UploadLessonVideoResponse
import io.loomus.edu.modules.program.entities.lesson.LessonEntityRepository
import io.loomus.edu.modules.program.entities.lesson_video.LessonVideoEntity
import io.loomus.edu.modules.program.entities.lesson_video.LessonVideoEntityRepository
import io.loomus.edu.security.permission.PermissionService
import io.loomus.edu.services.vimeo.GenerateVimeoBody
import io.loomus.edu.services.vimeo.VimeoService
import io.loomus.edu.utils.HttpResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class LessonVideoModifier(
    private val repository: LessonVideoEntityRepository,
    private val lessonRepository: LessonEntityRepository,
    private val vimeoService: VimeoService,
    private val permissionService: PermissionService
) {

    fun createByLessonId(
        courseId: Int, lessonId: Int, body: CreateLessonVideoBody
    ): LessonVideoResponse {
        if (repository.existsById(lessonId))
            throw HttpResponse.badRequest()
        val entity = repository.save(body.toEntity(courseId, lessonId))

        return LessonVideoResponse(entity, "complete")
    }


    fun generateUploadUrl(
        courseId: Int, lessonId: Int, size: Long
    ): UploadLessonVideoResponse {
        if (!permissionService.canAddVideo(courseId))
            throw HttpResponse.forbidden()
        val lesson = lessonRepository.findByCourseIdAndId(courseId, lessonId)
            ?: throw HttpResponse.notFound()
        if (repository.existsById(lessonId))
            throw HttpResponse.badRequest()
        val vimeoBody = vimeoService.generateUploadUrl(GenerateVimeoBody.url(name = lesson.name, size = size))
            ?: throw HttpResponse.badRequest()
        val entity = repository.save(
            LessonVideoEntity(
                lessonId = lessonId,
                courseId = courseId,
                isActive = false,
                embedUrl = vimeoBody.player_embed_url,
                videoId = vimeoBody.uri.split("/").last(),
                type = LessonVideoEntity.Type.UPLOAD
            )
        )

        return UploadLessonVideoResponse(
            vimeoBody.upload.upload_link,
            LessonVideoResponse(entity, vimeoBody.upload.status)
        )
    }


    fun confirmUploadedFile(courseId: Int, lessonId: Int): HttpResponse {
        val resource = repository.findByIdOrNull(lessonId)
            ?: throw HttpResponse.notFound()
        if (resource.courseId != courseId)
            throw HttpResponse.badRequest()
        if (resource.isActive)
            throw HttpResponse.badRequest()
        resource.isActive = true
        repository.save(resource)

        permissionService.evictInfoByCourseId(courseId)

        return HttpResponse.success()
    }


    fun deleteByLessonId(courseId: Int, lessonId: Int): HttpResponse {
        val entity = repository.findByCourseIdAndLessonId(courseId, lessonId)
            ?: throw HttpResponse.notFound()
        repository.delete(entity)
        if (entity.type == LessonVideoEntity.Type.UPLOAD) {
            vimeoService.deleteVideo(entity.videoId)
            permissionService.evictInfoByCourseId(courseId)
        }

        return HttpResponse.success()
    }

}