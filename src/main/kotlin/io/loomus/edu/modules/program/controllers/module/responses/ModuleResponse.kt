package io.loomus.edu.modules.program.controllers.module.responses

import io.loomus.edu.modules.program.controllers.lesson.responses.LessonResponse
import io.loomus.edu.modules.program.entities.module.ModuleEntity

data class ModuleResponse(
    val id: Int,
    val courseId: Int,
    val index: Int,
    val name: String,
    var lessons: List<LessonResponse>
) {

    constructor(entity: ModuleEntity, lessons: List<LessonResponse> = listOf()) : this(
        id = entity.id,
        courseId = entity.courseId,
        index = entity.index,
        name = entity.name,
        lessons = lessons
    )

}
