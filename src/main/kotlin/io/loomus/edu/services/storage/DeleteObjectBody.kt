package io.loomus.edu.services.storage

import io.loomus.edu.config.AppConstants

class DeleteObjectBody(
    val path: String,
    val bucket: String,
    val secret: String,
    val key: String
) {
    companion object {
        fun default(path: String) = DeleteObjectBody(
            path = path,
            bucket = AppConstants.S3Client.spaceBucket,
            secret = AppConstants.S3Client.spaceSecret,
            key = AppConstants.S3Client.spaceKey
        )
    }
}