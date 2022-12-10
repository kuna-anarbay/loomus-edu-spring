package io.loomus.edu.extensions

inline fun <reified T> Collection<T>.nullIfNeeded(): Collection<T>? {
    return ifEmpty { null }
}

