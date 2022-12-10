package io.loomus.edu.services.storage

import com.amazonaws.services.s3.model.CannedAccessControlList
import io.loomus.edu.config.AppConstants
import org.springframework.web.multipart.MultipartFile

class UploadObjectBody(
    val file: MultipartFile,
    val path: String,
    val bucket: String,
    val secret: String,
    val key: String,
    val access: CannedAccessControlList
) {
    companion object {
        fun public(file: MultipartFile, path: String) = UploadObjectBody(
            file = file,
            path = path,
            bucket = AppConstants.S3Client.spaceBucket,
            secret = AppConstants.S3Client.spaceSecret,
            key = AppConstants.S3Client.spaceKey,
            access = CannedAccessControlList.PublicRead
        )

        fun private(file: MultipartFile, path: String) = UploadObjectBody(
            file = file,
            path = path,
            bucket = AppConstants.S3Client.spaceBucket,
            secret = AppConstants.S3Client.spaceSecret,
            key = AppConstants.S3Client.spaceKey,
            access = CannedAccessControlList.Private
        )
    }
}