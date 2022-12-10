package io.loomus.edu.modules.program.controllers.lesson_package_relation.requests

import javax.validation.constraints.NotNull

class CreateLessonPackageRelationsBody(

    @field: NotNull
    val lessonIds: List<Int>

)