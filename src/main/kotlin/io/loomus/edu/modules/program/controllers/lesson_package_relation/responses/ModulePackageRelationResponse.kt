package io.loomus.edu.modules.program.controllers.lesson_package_relation.responses

import io.loomus.edu.modules.program.controllers.module.responses.ModuleResponse

data class ModulePackageRelationResponse(
    val module: ModuleResponse,
    val lessons: List<LessonPackageRelationResponse>
)
