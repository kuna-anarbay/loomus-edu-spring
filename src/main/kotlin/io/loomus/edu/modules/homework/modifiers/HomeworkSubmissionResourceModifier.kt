package io.loomus.edu.modules.homework.modifiers

import io.loomus.edu.modules.homework.controllers.homework_submission_resource.responses.HomeworkSubmissionResourceResponse
import io.loomus.edu.modules.homework.controllers.homework_submission_resource.responses.UploadHomeworkSubmissionResourceResponse
import io.loomus.edu.modules.homework.entities.homework_submission_resource.HomeworkSubmissionResourceEntity
import io.loomus.edu.modules.homework.entities.homework_submission_resource.HomeworkSubmissionResourceEntityRepository
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
class HomeworkSubmissionResourceModifier(
    private val repository: HomeworkSubmissionResourceEntityRepository,
    private val storageService: StorageService,
    private val permissionService: PermissionService
) {

    companion object {
        fun getPath(courseId: Int, mediaId: Int, extension: String) =
            "course-homework-submission-resources/course-$courseId/res-$mediaId.$extension"
    }


    fun generateUploadUrl(
        courseId: Int, homeworkId: Int, studentId: Int, size: Long, extension: String, name: String
    ): UploadHomeworkSubmissionResourceResponse {
        if (!permissionService.canUpload(courseId, size))
            throw HttpResponse.forbidden()
        val entity = HomeworkSubmissionResourceEntity(
            courseId = courseId,
            homeworkId = homeworkId,
            studentId = studentId,
            name = name,
            size = size,
            isActive = false
        )
        val resource = repository.save(entity)
        entity.path = getPath(courseId, resource.id, extension)
        repository.save(resource)

        val url = storageService.generateUploadUrl(GetSignedUrlBody.default(entity.path))

        return UploadHomeworkSubmissionResourceResponse(url, HomeworkSubmissionResourceResponse(resource))
    }


    fun confirmUploadedFile(courseId: Int, homeworkId: Int, studentId: Int, resourceId: Int): HttpResponse {
        val resource = repository.findByIdOrNull(resourceId)
            ?: throw HttpResponse.notFound()
        if (resource.studentId != studentId)
            throw HttpResponse.badRequest()
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


    fun deleteByCourseIdAndHomeworkId(courseId: Int, homeworkId: Int, studentId: Int) {
        val resources = repository.findAllByHomeworkIdAndStudentId(homeworkId, studentId)
        resources.forEach { resource ->
            storageService.delete(DeleteObjectBody.default(resource.path))
            repository.delete(resource)
        }
        permissionService.evictInfoByCourseId(courseId)
    }


    fun deleteByCourseIdAndId(courseId: Int, resourceId: Int): HttpResponse {
        val resource = repository.findByIdOrNull(resourceId)
            ?: throw HttpResponse.notFound()
        if (resource.courseId != courseId) throw HttpResponse.badRequest()
        storageService.delete(DeleteObjectBody.default(resource.path))
        repository.delete(resource)
        permissionService.evictInfoByCourseId(courseId)

        return HttpResponse.success()
    }

}