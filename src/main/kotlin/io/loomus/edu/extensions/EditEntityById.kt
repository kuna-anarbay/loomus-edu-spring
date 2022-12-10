package io.loomus.edu.extensions

import io.loomus.edu.utils.HttpResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

inline fun <reified T, reified ID: Any> JpaRepository<T, ID>.editById(id: ID, completion: (T) -> Unit) {
    val entity = findByIdOrNull(id) ?: throw HttpResponse.notFound()
    completion(entity)
    save(entity)
}