package io.loomus.edu.utils

data class PaginateResponse<T>(
    val total: Long,
    val data: List<T>
)