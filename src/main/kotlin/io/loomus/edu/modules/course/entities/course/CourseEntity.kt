package io.loomus.edu.modules.course.entities.course

import io.loomus.edu.utils.BaseEntity
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "course")
class CourseEntity(

    @Column
    var username: String = "",

    @Column
    var name: String = "",

    @Column
    var description: String? = null,

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

) : BaseEntity()