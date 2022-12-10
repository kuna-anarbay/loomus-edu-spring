package io.loomus.edu.services.vimeo

import io.loomus.edu.config.AppConstants
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class VimeoService(
    private val restTemplate: RestTemplate
) {

    fun generateUploadUrl(body: GenerateVimeoBody): VimeoResponse? {
        val url = "https://api.vimeo.com/me/videos"
        val headers = HttpHeaders()
        headers.set("Authorization", "bearer ${AppConstants.Vimeo.accessToken}")
        headers.set("Content-Type", "application/json")
        headers.set("Accept", "application/vnd.vimeo.*+json;version=3.4")

        return restTemplate.postForEntity(url, HttpEntity(body, headers), VimeoResponse::class.java).body
    }


    fun getStatus(videoId: String): VimeoStatusResponse? {
        val url = "https://api.vimeo.com/videos/${videoId}"
        val headers = HttpHeaders()
        headers.set("Authorization", "bearer ${AppConstants.Vimeo.accessToken}")

        return restTemplate.exchange(
            url,
            HttpMethod.GET,
            HttpEntity<Any>(headers),
            VimeoStatusResponse::class.java
        ).body
    }


    fun deleteVideo(videoId: String) {
        val url = "https://api.vimeo.com/videos/${videoId}"
        val headers = HttpHeaders()
        headers.set("Authorization", "bearer ${AppConstants.Vimeo.accessToken}")

        restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity<Any>(headers), Void::class.java)
    }

}