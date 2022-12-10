package io.loomus.edu.modules.user.controllers.user_session.requests

import javax.validation.constraints.NotNull

class EditDeviceTokenRequest(

    @field: NotNull
    val refreshToken: String,

    val deviceToken: String?

)