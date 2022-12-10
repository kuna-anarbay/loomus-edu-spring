package io.loomus.edu.modules.user.entities.user_avatar

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "user_avatar")
class UserAvatarEntity(

    @Id
    @Column(name = "user_id")
    val userId: Int = 0,

    @Column
    var path: String = "",

    @Column
    var version: Int = 1,

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now()

)