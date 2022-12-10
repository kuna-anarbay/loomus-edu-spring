package io.loomus.edu.modules.program.controllers.lesson.responses

import io.loomus.edu.modules.homework.controllers.homework.responses.HomeworkResponse

data class LessonDetailsResponse(
    val lesson: LessonResponse,
    val homework: HomeworkResponse?
)
