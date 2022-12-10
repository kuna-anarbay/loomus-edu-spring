package io.loomus.edu.services.storage

import com.amazonaws.HttpMethod
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import io.loomus.edu.config.AppConstants
import org.springframework.stereotype.Service
import java.net.URL
import java.util.*


@Service
class StorageService {

    fun upload(body: UploadObjectBody) {
        if (AppConstants.environment == AppConstants.Environment.DEV) return
        val s3Client = getS3Client(body.key, body.secret)
        val metadata = ObjectMetadata()
        metadata.contentLength = body.file.inputStream.available().toLong()
        if (body.file.contentType != null && "" != body.file.contentType) {
            metadata.contentType = body.file.contentType
        }

        s3Client.putObject(
            PutObjectRequest(body.bucket, body.path, body.file.inputStream, metadata)
                .withCannedAcl(body.access)
        )
    }


    fun delete(body: DeleteObjectBody) {
        val s3Client = getS3Client(body.key, body.secret)
        s3Client.deleteObject(body.bucket, body.path)
    }


    fun getFileSize(body: GetSignedUrlBody): Long {
        val s3Client = getS3Client(body.key, body.secret)

        return s3Client.getObject(AppConstants.S3Client.spaceBucket, body.path)
            .objectMetadata.contentLength
    }


    fun generateUploadUrl(body: GetSignedUrlBody): URL {
        val s3Client = getS3Client(body.key, body.secret)
        val expirationPeriod = 90 * 60 * 1000
        val expirationDate = Date(System.currentTimeMillis() + expirationPeriod)

        return s3Client.generatePresignedUrl(
            AppConstants.S3Client.spaceBucket,
            body.path,
            expirationDate,
            HttpMethod.PUT
        )
    }


    fun generateDownloadUrl(body: GetSignedUrlBody): URL {
        val s3Client = getS3Client(body.key, body.secret)
        val expirationPeriod = 90 * 60 * 1000
        val expirationDate = Date(System.currentTimeMillis() + expirationPeriod)

        return s3Client.generatePresignedUrl(
            AppConstants.S3Client.spaceBucket,
            body.path,
            expirationDate,
            HttpMethod.GET
        )
    }


    private fun getS3Client(key: String, secret: String): AmazonS3 {
        val credentials = BasicAWSCredentials(key, secret)
        val endpoint = AwsClientBuilder.EndpointConfiguration(
            AppConstants.S3Client.spaceEndpoint,
            AppConstants.S3Client.spaceRegion
        )

        return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(endpoint)
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .build()
    }

}