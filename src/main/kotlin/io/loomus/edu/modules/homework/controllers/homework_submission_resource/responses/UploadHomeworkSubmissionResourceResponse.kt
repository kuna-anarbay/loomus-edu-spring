package io.loomus.edu.modules.homework.controllers.homework_submission_resource.responses

import java.net.URL

data class UploadHomeworkSubmissionResourceResponse(
    val url: URL,
    val resource: HomeworkSubmissionResourceResponse
)
