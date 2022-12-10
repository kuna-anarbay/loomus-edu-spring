package io.loomus.edu.exception

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.server.ResponseStatusException
import javax.persistence.EntityNotFoundException
import javax.persistence.NoResultException

@ControllerAdvice(basePackages = ["io.loomus.edu"])
class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(e: ResponseStatusException) = generateError(
        status = e.status,
        message = e.reason,
        e = e
    )

    @ExceptionHandler(
        ConstraintViolationException::class,
        HttpClientErrorException.BadRequest::class,
        MethodArgumentNotValidException::class,
        MissingServletRequestParameterException::class,
        IllegalArgumentException::class,
        MissingKotlinParameterException::class,
        HttpMessageNotReadableException::class
    )
    fun constraintViolationException(e: Exception) = generateError(
        status = HttpStatus.BAD_REQUEST,
        message = e.message,
        e = e
    )

    @ExceptionHandler(
        EntityNotFoundException::class,
        NoSuchElementException::class,
        NoResultException::class,
        EmptyResultDataAccessException::class,
        IndexOutOfBoundsException::class,
        KotlinNullPointerException::class
    )
    fun notFoundException(e: Exception) = generateError(
        HttpStatus.NOT_FOUND,
        message = "Resource not found",
        e = e
    )

    @ExceptionHandler(Exception::class)
    fun internalServerErrorException(e: Exception) = generateError(
        HttpStatus.INTERNAL_SERVER_ERROR,
        message = "Generic internal error",
        e = e
    )

    private fun generateError(
        status: HttpStatus,
        message: String?,
        e: Exception
    ): ResponseEntity<ErrorResponse> {
        val trace = e.stackTraceToString()

        return ResponseEntity(
            ErrorResponse(
                status = status,
                message = message,
                trace = trace
            ), status
        )
    }

}