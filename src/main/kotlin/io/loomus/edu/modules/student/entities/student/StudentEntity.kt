package io.loomus.edu.modules.student.entities.student

import io.loomus.edu.utils.BaseEntity
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "student")
class StudentEntity(

    @Column(name = "course_id")
    val courseId: Int = 0,

    @Column(name = "package_id")
    var packageId: Int = 0,

    @Column(name = "first_name")
    var firstName: String = "",

    @Column(name = "last_name")
    var lastName: String? = null,

    @Column
    var phone: String = "",

    @Column(name = "is_active")
    var isActive: Boolean = false,

    @Column(name = "last_opened_at")
    var lastOpenedAt: LocalDateTime? = null,

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now()

) : BaseEntity()