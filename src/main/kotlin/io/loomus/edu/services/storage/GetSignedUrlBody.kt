package io.loomus.edu.services.storage

import io.loomus.edu.config.AppConstants

class GetSignedUrlBody(
    val path: String,
    val secret: String,
    val key: String
) {
    companion object {
        fun default(path: String) = GetSignedUrlBody(
            path = path,
            secret = AppConstants.S3Client.spaceSecret,
            key = AppConstants.S3Client.spaceKey
        )
    }
}