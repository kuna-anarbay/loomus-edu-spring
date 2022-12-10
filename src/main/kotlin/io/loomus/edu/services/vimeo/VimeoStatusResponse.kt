package io.loomus.edu.services.vimeo

data class VimeoStatusResponse(
    val upload: Upload
) {

    data class Upload(
        val status: String
    )

}
