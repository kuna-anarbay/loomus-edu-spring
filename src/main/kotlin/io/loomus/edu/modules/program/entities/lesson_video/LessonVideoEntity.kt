package io.loomus.edu.modules.program.entities.lesson_video

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "lesson_video")
class LessonVideoEntity(

    @Id
    @Column(name = "lesson_id")
    val lessonId: Int = 0,

    @Column(name = "course_id")
    val courseId: Int = 0,

    @Column(name = "video_id")
    var videoId: String = "",

    @Column(name = "embed_url")
    val embedUrl: String = "",

    @Column
    @Enumerated(value = EnumType.STRING)
    val type: Type = Type.UPLOAD,

    @Column(name = "is_active")
    var isActive: Boolean = false,

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now()

) {

    enum class Type {
        YOUTUBE, VIMEO, UPLOAD
    }
}