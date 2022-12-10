package io.loomus.edu.modules.homework.controllers.homework.responses

import io.loomus.edu.extensions.seconds
import io.loomus.edu.modules.homework.controllers.homework_resource.responses.HomeworkResourceResponse
import io.loomus.edu.modules.homework.controllers.homework_submission.responses.HomeworkSubmissionResponse
import io.loomus.edu.modules.homework.entities.homework.HomeworkEntity
import io.loomus.edu.modules.homework.entities.homework_resource.HomeworkResourceEntity

data class HomeworkResponse(
    val id: Int,
    val courseId: Int,
    val value: String,
    val deadlineAt: Int?,
    val resources: List<HomeworkResourceResponse>,
    val submission: HomeworkSubmissionResponse?
) {

    constructor(
        entity: HomeworkEntity,
        resources: List<HomeworkResourceEntity> = listOf(),
        submission: HomeworkSubmissionResponse? = null
    ) : this(
        courseId = entity.courseId,
        id = entity.id,
        value = entity.value,
        deadlineAt = entity.deadlineAt?.seconds,
        resources = resources.map { HomeworkResourceResponse(it) },
        submission = submission
    )

}
