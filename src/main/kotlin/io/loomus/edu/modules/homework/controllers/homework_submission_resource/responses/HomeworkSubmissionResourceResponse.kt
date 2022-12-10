package io.loomus.edu.modules.homework.controllers.homework_submission_resource.responses

import io.loomus.edu.modules.homework.entities.homework_submission_resource.HomeworkSubmissionResourceEntity

data class HomeworkSubmissionResourceResponse(
    val id: Int,
    val homeworkId: Int,
    val studentId: Int,
    val courseId: Int,
    val path: String,
    val size: Long,
    val name: String
) {

    constructor(entity: HomeworkSubmissionResourceEntity) : this(
        id = entity.id,
        homeworkId = entity.homeworkId,
        studentId = entity.studentId,
        courseId = entity.courseId,
        name = entity.name,
        path = entity.path,
        size = entity.size
    )

}
