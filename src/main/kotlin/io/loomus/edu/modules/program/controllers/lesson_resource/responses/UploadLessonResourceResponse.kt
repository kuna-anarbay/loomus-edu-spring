package io.loomus.edu.modules.program.controllers.lesson_resource.responses

import java.net.URL

data class UploadLessonResourceResponse(
    val url: URL,
    val resource: LessonResourceResponse
)
