package io.loomus.edu.modules.program.entities.lesson

import io.loomus.edu.utils.BaseEntity
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "lesson")
class LessonEntity(

    @Column(name = "course_id")
    val courseId: Int = 0,

    @Column(name = "module_id")
    val moduleId: Int = 0,

    @Column(name = "`index`")
    var index: Int = 1,

    @Column
    var name: String = "",

    @Column
    var description: String? = null,

    @Column
    @Enumerated(value = EnumType.STRING)
    var status: Status = Status.DRAFT,

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now()

) : BaseEntity() {

    enum class Status {
        DRAFT, VISIBLE, ACTIVE
    }

}