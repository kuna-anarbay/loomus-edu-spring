package io.loomus.edu.modules.course.controllers.course_package.requests

import io.loomus.edu.modules.course.entities.course_package.CoursePackageEntity
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class CreatePackageBody(

    @field: NotNull
    @field: NotBlank
    @field: Size(min = 1, max = 255)
    val name: String,

    @field: NotNull
    val homeworkAvailable: Boolean

) {

    fun toEntity(courseId: Int) = CoursePackageEntity(
        courseId = courseId,
        name = name,
        homeworkAvailable = homeworkAvailable
    )

}