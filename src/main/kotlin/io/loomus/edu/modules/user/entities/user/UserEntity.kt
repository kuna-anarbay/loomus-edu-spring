package io.loomus.edu.modules.user.entities.user

import io.loomus.edu.utils.BaseEntity
import io.loomus.edu.utils.Gender
import io.loomus.edu.utils.Language
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "user")
class UserEntity(

    @Column(unique = true)
    val phone: String = "",

    @Column
    var password: String = "",

    @Column(name = "first_name")
    var firstName: String = "",

    @Column(name = "last_name")
    var lastName: String? = null,

    @Column
    @Enumerated(value = EnumType.STRING)
    var gender: Gender? = null,

    @Column
    var birthday: Int? = null,

    @Column
    @Enumerated(value = EnumType.STRING)
    var language: Language = Language.RU,

    @Column
    @Enumerated(value = EnumType.STRING)
    var status: Status = Status.ACTIVE,

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now()

) : BaseEntity() {

    enum class Status {
        ACTIVE,
        DELETED,
        BLOCKED
    }
}