package io.loomus.edu.modules.homework.controllers.homework_submission.requests

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class ReplyHomeworkSubmissionBody(

    @field: NotBlank
    @field: Pattern(regexp = "^(PENDING|ACCEPTED|DECLINED)$")
    val status: String,

    val notes: String?
)