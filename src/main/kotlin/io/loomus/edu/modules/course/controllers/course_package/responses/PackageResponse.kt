package io.loomus.edu.modules.course.controllers.course_package.responses

import io.loomus.edu.modules.course.entities.course_package.CoursePackageEntity

data class PackageResponse(
    val id: Int,
    val courseId: Int,
    val name: String,
    val lessonsCount: Long,
    val homeworkAvailable: Boolean
) {

    constructor(entity: CoursePackageEntity, lessonsCount: Long = 0) : this(
        id = entity.id,
        courseId = entity.courseId,
        name = entity.name,
        lessonsCount = lessonsCount,
        homeworkAvailable = entity.homeworkAvailable
    )

}
