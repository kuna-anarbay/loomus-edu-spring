package io.loomus.edu.modules.program.controllers.lesson_package_relation.responses

import io.loomus.edu.modules.program.controllers.lesson.responses.LessonResponse

data class LessonPackageRelationResponse(
    val lesson: LessonResponse,
    val isConnected: Boolean
)
