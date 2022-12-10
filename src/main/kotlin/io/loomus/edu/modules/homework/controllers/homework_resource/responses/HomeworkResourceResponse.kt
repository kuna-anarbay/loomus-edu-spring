package io.loomus.edu.modules.homework.controllers.homework_resource.responses

import io.loomus.edu.modules.homework.entities.homework_resource.HomeworkResourceEntity

data class HomeworkResourceResponse(
    val id: Int,
    val homeworkId: Int,
    val courseId: Int,
    val path: String,
    val name: String,
    val size: Long
) {

    constructor(entity: HomeworkResourceEntity) : this(
        id = entity.id,
        homeworkId = entity.homeworkId,
        courseId = entity.courseId,
        path = entity.path,
        name = entity.name,
        size = entity.size
    )

}
