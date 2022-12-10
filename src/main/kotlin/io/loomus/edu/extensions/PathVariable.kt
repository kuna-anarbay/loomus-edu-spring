package io.loomus.edu.extensions

import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.servlet.HandlerMapping

fun NativeWebRequest.getPathVariable(name: String): Int? {
    val variables =
        (this as? ServletWebRequest?)?.request?.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)

    return ((variables as? Map<*, *>?)?.get(name) as? String?)?.toInt()
}