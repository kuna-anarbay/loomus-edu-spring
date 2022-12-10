package io.loomus.edu.modules.program.modifiers

import io.loomus.edu.modules.program.controllers.lesson_resource.responses.LessonResourceResponse
import io.loomus.edu.modules.program.controllers.lesson_resource.responses.UploadLessonResourceResponse
import io.loomus.edu.modules.program.entities.lesson_resource.LessonResourceEntity
import io.loomus.edu.modules.program.entities.lesson_resource.LessonResourceEntityRepository
import io.loomus.edu.security.permission.PermissionService
import io.loomus.edu.services.storage.DeleteObjectBody
import io.loomus.edu.services.storage.GetSignedUrlBody
import io.loomus.edu.services.storage.StorageService
import io.loomus.edu.utils.HttpResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional
import kotlin.math.abs

@Service
@Transactional
class LessonResourceModifier(
    private val repository: LessonResourceEntityRepository,
    private val storageService: StorageService,
    private val permissionService: PermissionService
) {

    companion object {
        fun getPath(courseId: Int, mediaId: Int, extension: String) =
            "course-lesson-resources/course-$courseId/res-$mediaId.$extension"
    }


    fun generateUploadUrl(
        courseId: Int, lessonId: Int, size: Long, extension: String, name: String
    ): UploadLessonResourceResponse {
        if (!permissionService.canUpload(courseId, size))
            throw HttpResponse.forbidden()
        val entity = LessonResourceEntity(
            courseId = courseId,
            lessonId = lessonId,
            name = name,
            size = size,
            isActive = false
        )
        val resource = repository.save(entity)
        entity.path = getPath(courseId, resource.id, extension)
        repository.save(resource)

        val url = storageService.generateUploadUrl(GetSignedUrlBody.default(entity.path))

        return UploadLessonResourceResponse(url, LessonResourceResponse(resource))
    }


    fun confirmUploadedFile(courseId: Int, lessonId: Int, resourceId: Int): HttpResponse {
        val resource = repository.findByIdOrNull(resourceId)
            ?: throw HttpResponse.notFound()
        if (resource.courseId != courseId)
            throw HttpResponse.badRequest()
        if (abs(resource.size - storageService.getFileSize(GetSignedUrlBody.default(resource.path))) > 1024)
            throw HttpResponse.badRequest()
        if (resource.isActive)
            throw HttpResponse.badRequest()
        resource.isActive = true
        repository.save(resource)

        permissionService.evictInfoByCourseId(courseId)

        return HttpResponse.success()
    }


    fun deleteByCourseIdAndLessonId(courseId: Int, lessonId: Int) {
        val resources = repository.findAllByCourseIdAndLessonId(courseId, lessonId)
        resources.forEach { resource ->
            storageService.delete(DeleteObjectBody.default(resource.path))
            repository.delete(resource)
        }

        permissionService.evictInfoByCourseId(courseId)
    }


    fun deleteByCourseIdAndId(courseId: Int, resourceId: Int): HttpResponse {
        val resource = repository.findByIdOrNull(resourceId) ?: throw HttpResponse.notFound()
        if (resource.courseId != courseId) throw HttpResponse.badRequest()
        storageService.delete(DeleteObjectBody.default(resource.path))
        repository.delete(resource)
        permissionService.evictInfoByCourseId(courseId)

        return HttpResponse.success()
    }

}