package io.loomus.edu.extensions

val String.nullIfNeeded: String?
    get() {
        if (isBlank()) return null
        return this
    }