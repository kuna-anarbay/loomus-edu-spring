package io.loomus.edu.modules.program.entities.lesson_resource

import io.loomus.edu.utils.BaseEntity
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "lesson_resource")
class LessonResourceEntity(

    @Column(name = "course_id")
    val courseId: Int = 0,

    @Column(name = "lesson_id")
    val lessonId: Int = 0,

    @Column(name = "is_active")
    var isActive: Boolean = false,

    @Column
    var path: String = "",

    @Column
    val name: String = "",

    @Column
    val size: Long = 0,

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now()

) : BaseEntity()