package io.loomus.edu.modules.program.controllers.module.requests

import io.loomus.edu.modules.program.entities.module.ModuleEntity
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class CreateModuleBody(

    @field: NotNull
    @field: NotBlank
    val name: String
) {

    fun toEntity(courseId: Int, index: Int) = ModuleEntity(
        courseId = courseId,
        name = name,
        index = index
    )

}