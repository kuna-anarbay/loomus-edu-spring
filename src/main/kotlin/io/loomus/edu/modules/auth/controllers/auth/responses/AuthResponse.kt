package io.loomus.edu.modules.auth.controllers.auth.responses

import java.io.Serializable

data class AuthResponse(
    val expiresIn: Int,
    val accessToken: String,
    val refreshToken: String
) : Serializable
