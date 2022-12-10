package io.loomus.edu.modules.program.controllers.lesson_resource.responses

import io.loomus.edu.modules.program.entities.lesson_resource.LessonResourceEntity

data class LessonResourceResponse(
    val id: Int,
    val courseId: Int,
    val lessonId: Int,
    val path: String,
    val name: String,
    val size: Long
) {

    constructor(entity: LessonResourceEntity) : this(
        id = entity.id,
        courseId = entity.courseId,
        lessonId = entity.lessonId,
        path = entity.path,
        name = entity.name,
        size = entity.size
    )

}
