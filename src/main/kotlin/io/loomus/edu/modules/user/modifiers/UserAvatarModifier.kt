package io.loomus.edu.modules.user.modifiers

import io.loomus.edu.utils.HttpResponse
import io.loomus.edu.extensions.extension
import io.loomus.edu.extensions.validateSize
import io.loomus.edu.modules.user.entities.user_avatar.UserAvatarEntity
import io.loomus.edu.modules.user.entities.user_avatar.UserAvatarEntityRepository
import io.loomus.edu.services.storage.DeleteObjectBody
import io.loomus.edu.services.storage.StorageService
import io.loomus.edu.services.storage.UploadObjectBody
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class UserAvatarModifier(
    private val repository: UserAvatarEntityRepository,
    private val storageService: StorageService
) {

    companion object {
        fun getPath(userId: Int, extension: String) =
            "user-avatars/pic-$userId.$extension"
    }


    fun saveById(userId: Int, file: MultipartFile): String {
        file.validateSize(1)
        val entity = repository.findByIdOrNull(userId) ?: UserAvatarEntity(userId = userId, version = 0)
        if (entity.version != 0) {
            storageService.delete(DeleteObjectBody.default(entity.path))
        }
        entity.version++
        entity.path = getPath(userId, file.extension) + "?v=${entity.version}"
        storageService.upload(UploadObjectBody.public(file, getPath(userId, file.extension)))
        repository.save(entity)

        return entity.path
    }


    fun deleteById(userId: Int) {
        val entity = repository.findByIdOrNull(userId) ?: throw HttpResponse.notFound()
        storageService.delete(DeleteObjectBody.default(entity.path))
        repository.delete(entity)
    }

}