package io.loomus.edu.modules.homework.controllers.homework_submission.responses

import io.loomus.edu.extensions.seconds
import io.loomus.edu.modules.homework.controllers.homework_submission_resource.responses.HomeworkSubmissionResourceResponse
import io.loomus.edu.modules.homework.entities.homework_submission.HomeworkSubmissionEntity
import io.loomus.edu.modules.homework.entities.homework_submission_resource.HomeworkSubmissionResourceEntity
import io.loomus.edu.modules.student.controllers.student.responses.StudentResponse
import io.loomus.edu.modules.student.entities.student.StudentEntity

data class HomeworkSubmissionResponse(
    val homeworkId: Int,
    val studentId: Int,
    val courseId: Int,
    val student: StudentResponse,
    val value: String?,
    val status: HomeworkSubmissionEntity.Status,
    val triesCount: Int,
    val notes: String?,
    val submittedAt: Int,
    val resources: List<HomeworkSubmissionResourceResponse>
) {

    constructor(
        entity: HomeworkSubmissionEntity,
        student: StudentEntity,
        resources: List<HomeworkSubmissionResourceEntity>
    ) : this(
        homeworkId = entity.id.homeworkId,
        studentId = entity.id.studentId,
        courseId = entity.courseId,
        student = StudentResponse(student),
        value = entity.value,
        status = entity.status,
        triesCount = entity.triesCount,
        notes = entity.notes,
        submittedAt = entity.submittedAt.seconds,
        resources = resources.map { HomeworkSubmissionResourceResponse(it) }
    )

}
