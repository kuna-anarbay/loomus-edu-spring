package io.loomus.edu.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import io.loomus.edu.config.AppConstants
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3ClientConfig {

    @Bean
    fun getS3(): AmazonS3 {
        val credentials = BasicAWSCredentials(AppConstants.S3Client.spaceKey, AppConstants.S3Client.spaceSecret)
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