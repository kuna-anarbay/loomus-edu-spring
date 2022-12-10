package io.loomus.edu.modules.homework.entities.homework

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "homework")
class HomeworkEntity(

    @Id
    @Column
    val id: Int = 0,

    @Column(name = "course_id")
    val courseId: Int = 0,

    @Column
    var value: String = "",

    @Column(name = "deadline_at")
    var deadlineAt: LocalDateTime? = null,

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now()

)