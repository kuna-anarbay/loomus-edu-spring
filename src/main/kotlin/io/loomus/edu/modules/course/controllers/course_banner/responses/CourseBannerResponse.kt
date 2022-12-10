package io.loomus.edu.modules.course.controllers.course_banner.responses

import io.loomus.edu.modules.course.entities.course_banner.CourseBannerEntity
import java.io.Serializable

data class CourseBannerResponse(
    val courseId: Int,
    val path: String,
    val version: Int
) : Serializable {

    constructor(entity: CourseBannerEntity) : this(
        courseId = entity.courseId,
        path = entity.path,
        version = entity.version
    )

}
