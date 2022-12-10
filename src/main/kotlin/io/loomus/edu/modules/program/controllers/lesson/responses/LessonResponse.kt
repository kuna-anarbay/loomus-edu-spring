package io.loomus.edu.modules.program.controllers.lesson.responses

import io.loomus.edu.modules.course.controllers.course_package.responses.PackageResponse
import io.loomus.edu.modules.program.controllers.lesson_resource.responses.LessonResourceResponse
import io.loomus.edu.modules.program.controllers.lesson_video.responses.LessonVideoResponse
import io.loomus.edu.modules.program.entities.lesson.LessonEntity

data class LessonResponse(
    val id: Int,
    val courseId: Int,
    val moduleId: Int,
    val index: Int,
    val name: String,
    val description: String?,
    val status: LessonEntity.Status,
    val video: LessonVideoResponse?,
    val homeworkPassed: Boolean,
    val resources: List<LessonResourceResponse>,
    val packages: List<PackageResponse>
) {

    constructor(
        entity: LessonEntity,
        video: LessonVideoResponse? = null,
        resources: List<LessonResourceResponse> = listOf(),
        homeworkPassed: Boolean = false,
        packages: List<PackageResponse> = listOf()
    ) : this(
        id = entity.id,
        moduleId = entity.moduleId,
        courseId = entity.courseId,
        index = entity.index,
        name = entity.name,
        description = entity.description,
        status = entity.status,
        homeworkPassed = homeworkPassed,
        resources = resources,
        packages = packages,
        video = video
    )

}
