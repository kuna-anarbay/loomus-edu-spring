package io.loomus.edu.modules.course.controllers.course.responses

import io.loomus.edu.modules.course.controllers.course_banner.responses.CourseBannerResponse
import io.loomus.edu.modules.course.entities.course.CourseEntity
import io.loomus.edu.modules.course.entities.course_banner.CourseBannerEntity
import java.io.Serializable

data class CourseResponse(
    val id: Int,
    val username: String,
    val name: String,
    val description: String?,
    val isActive: Boolean,
    val banner: CourseBannerResponse?
) : Serializable {

    constructor(entity: CourseEntity, banner: CourseBannerEntity? = null) : this(
        id = entity.id,
        username = entity.username,
        name = entity.name,
        description = entity.description,
        isActive = entity.isActive,
        banner = banner?.let { CourseBannerResponse(it) }
    )

}
