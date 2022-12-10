package io.loomus.edu.modules.program.controllers.lesson_student_relation.responses

import io.loomus.edu.extensions.seconds
import io.loomus.edu.modules.program.entities.lesson_student_relation.LessonStudentRelationEntity

data class LessonStudentRelationResponse(
    val studentId: Int,
    val courseId: Int,
    val lessonId: Int,
    val homeworkPassed: Boolean,
    val createdAt: Int
) {

    constructor(entity: LessonStudentRelationEntity) : this(
        studentId = entity.id.studentId,
        lessonId = entity.id.lessonId,
        courseId = entity.courseId,
        homeworkPassed = entity.homeworkPassed,
        createdAt = entity.createdAt.seconds
    )

}
