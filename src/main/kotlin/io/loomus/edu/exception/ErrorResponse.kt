package io.loomus.edu.exception

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class ErrorResponse(
    status: HttpStatus,
    val message: String?,
    val trace: String? = null
) {
    private val code: Int
    private val status: String
    private val timestamp: String = LocalDateTime.now().toString()

    init {
        this.status = status.name
        code = status.value()
    }

    override fun toString(): String {
        return "{" +
                "\"code\": $code," +
                "\"status\": \"$status\"," +
                "\"timestamp\": \"$timestamp\"," +
                "\"trace\": \"$trace\"," +
                "\"message\": \"$message\"" +
                "}"
    }
}