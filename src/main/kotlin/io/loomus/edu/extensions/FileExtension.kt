package io.loomus.edu.extensions

import io.loomus.edu.utils.HttpResponse
import io.loomus.edu.utils.LocaledMessage
import org.springframework.web.multipart.MultipartFile
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow


val MultipartFile.extension: String
    get() {
        val name = originalFilename ?: name
        return name.substring(name.lastIndexOf(".") + 1).lowercase()
    }


val String.extension: String
    get() {
        return this.substring(this.lastIndexOf(".") + 1).lowercase()
    }


fun MultipartFile.validateSize(size: Long) {
    val bytes = size * 1_048_576
    val fileSize = readableFileSize(bytes)

    if (this.size >= bytes)
        throw HttpResponse.badRequest(LocaledMessage.localize("exception.resource.file-size-exceeds").format(fileSize))
}

private fun readableFileSize(size: Long): String {
    if (size <= 0) return "0"
    val units = arrayOf("B", "kB", "MB", "GB", "TB")
    val digitGroups = (Math.log10(size.toDouble()) / log10(1024.0)).toInt()
    return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble())) + " " + units[digitGroups]
}