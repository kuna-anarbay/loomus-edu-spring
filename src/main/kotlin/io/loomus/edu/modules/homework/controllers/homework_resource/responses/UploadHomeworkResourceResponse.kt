package io.loomus.edu.modules.homework.controllers.homework_resource.responses

import java.net.URL

data class UploadHomeworkResourceResponse(
    val url: URL,
    val resource: HomeworkResourceResponse
)
