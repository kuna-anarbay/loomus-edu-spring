package io.loomus.edu.modules.program.controllers.lesson_video.responses

import io.loomus.edu.modules.program.entities.lesson_video.LessonVideoEntity

data class LessonVideoResponse(
    val courseId: Int,
    val lessonId: Int,
    val videoId: String,
    val type: LessonVideoEntity.Type,
    val embedUrl: String,
    val status: String
) {

    constructor(entity: LessonVideoEntity, status: String) : this(
        courseId = entity.courseId,
        lessonId = entity.lessonId,
        videoId = entity.videoId,
        type = entity.type,
        embedUrl = entity.embedUrl,
        status = status
    )

}
