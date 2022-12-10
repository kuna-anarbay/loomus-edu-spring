package io.loomus.edu.services.vimeo

data class VimeoResponse(
    val uri: String,
    val link: String,
    val player_embed_url: String,
    val upload: Upload
) {

    data class Upload(
        val status: String,
        val upload_link: String
    )

}
