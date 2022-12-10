package io.loomus.edu.modules.staff.entities.staff

import io.loomus.edu.utils.BaseEntity
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "staff")
class StaffEntity(

    @Column(name = "course_id")
    val courseId: Int = 0,

    @Column(name = "first_name")
    var firstName: String = "",

    @Column(name = "last_name")
    var lastName: String? = null,

    @Column
    var phone: String = "",

    @Column
    @Enumerated(value = EnumType.STRING)
    val role: Role = Role.ADMIN,

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now()

) : BaseEntity() {

    enum class Role {
        OWNER, ADMIN, ASSISTANT
    }

}