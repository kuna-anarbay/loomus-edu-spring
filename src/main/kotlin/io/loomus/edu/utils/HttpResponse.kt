package io.loomus.edu.utils

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class HttpResponse(
    val status: Int,
    val message: String
) {

    companion object {

        fun success(message: String? = null): HttpResponse {
            return HttpResponse(status = 200, message = message ?: "OK")
        }

        fun badRequest(message: String? = null): ResponseStatusException {
            return ResponseStatusException(HttpStatus.BAD_REQUEST, message)
        }

        fun notFound(message: String? = null): ResponseStatusException {
            return ResponseStatusException(HttpStatus.NOT_FOUND, message)
        }

        fun forbidden(message: String? = null): ResponseStatusException {
            return ResponseStatusException(HttpStatus.FORBIDDEN, message)
        }

        fun unauthorized(message: String? = null): ResponseStatusException {
            return ResponseStatusException(HttpStatus.UNAUTHORIZED, message)
        }

    }
}