package io.loomus.edu.modules.course.modifiers

import io.loomus.edu.extensions.extension
import io.loomus.edu.extensions.validateSize
import io.loomus.edu.modules.course.controllers.course_banner.responses.CourseBannerResponse
import io.loomus.edu.modules.course.entities.course_banner.CourseBannerEntity
import io.loomus.edu.modules.course.entities.course_banner.CourseBannerEntityRepository
import io.loomus.edu.services.cache.CacheName
import io.loomus.edu.services.storage.DeleteObjectBody
import io.loomus.edu.services.storage.StorageService
import io.loomus.edu.services.storage.UploadObjectBody
import io.loomus.edu.utils.HttpResponse
import org.springframework.cache.annotation.CacheEvict
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class CourseBannerModifier(
    private val repository: CourseBannerEntityRepository,
    private val storageService: StorageService
) {

    companion object {
        fun getPath(courseId: Int, extension: String) =
            "courses/course-$courseId/banner.$extension"
    }


    @CacheEvict(value = [CacheName.course], key = "#courseId")
    fun saveById(courseId: Int, file: MultipartFile): CourseBannerResponse {
        file.validateSize(1)
        val entity = repository.findByIdOrNull(courseId)
            ?: CourseBannerEntity(courseId = courseId, version = 0)
        if (entity.version != 0) {
            storageService.delete(DeleteObjectBody.default(entity.path))
        }
        entity.version++
        entity.path = getPath(courseId, file.extension) + "?v=${entity.version}"
        storageService.upload(UploadObjectBody.public(file, getPath(courseId, file.extension)))
        repository.save(entity)

        return CourseBannerResponse(entity)
    }


    @CacheEvict(value = [CacheName.course], key = "#courseId")
    fun deleteById(courseId: Int): HttpResponse {
        val entity = repository.findByIdOrNull(courseId)
            ?: throw HttpResponse.notFound()
        storageService.delete(DeleteObjectBody.default(entity.path))
        repository.delete(entity)

        return HttpResponse.success()
    }

}