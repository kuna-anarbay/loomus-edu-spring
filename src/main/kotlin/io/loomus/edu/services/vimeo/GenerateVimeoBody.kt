package io.loomus.edu.services.vimeo

import io.loomus.edu.config.AppConstants

data class GenerateVimeoBody(
    val name: String,
    val embed_domains: Array<String>,
    val embed: Embed,
    val privacy: Privacy,
    val upload: Upload
) {

    companion object {

        fun url(name: String, size: Long) =
            GenerateVimeoBody(
                name = name,
                embed_domains = arrayOf(AppConstants.clientBaseUrl),
                embed = Embed(
                    buttons = Buttons(
                        embed = false,
                        like = false,
                        share = false,
                        watchlater = false
                    ),
                    color = "#128c7e",
                    logos = Logos(
                        vimeo = false
                    ),
                    title = Title(
                        name = "hide",
                        owner = "hide",
                        portrait = "hide"
                    )
                ),
                privacy = Privacy(
                    view = "unlisted",
                    embed = "whitelist",
                    download = false,
                    add = false,
                    comments = "nobody"
                ),
                upload = Upload(
                    approach = "tus",
                    size = "$size"
                )
            )
    }

    data class Buttons(
        val embed: Boolean,
        val like: Boolean,
        val share: Boolean,
        val watchlater: Boolean
    )

    data class Embed(
        val buttons: Buttons,
        val title: Title,
        val color: String,
        val logos: Logos
    )

    data class Logos(
        val vimeo: Boolean
    )

    data class Title(
        val name: String,
        val owner: String,
        val portrait: String
    )

    data class Privacy(
        val view: String,
        val embed: String,
        val download: Boolean,
        val add: Boolean,
        val comments: String
    )

    data class Upload(
        val approach: String,
        val size: String
    )

}
