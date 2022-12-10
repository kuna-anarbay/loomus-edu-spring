package io.loomus.edu.modules.user.entities.user_session

import io.loomus.edu.utils.BaseEntity
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "user_session")
class UserSessionEntity(

    @Column(name = "user_id")
    val userId: Int = 0,

    @Column(name = "is_active")
    var isActive: Boolean = false,

    @Column(name = "ip_address")
    var ipAddress: String = "",

    @Column(name = "device_type")
    var deviceType: String = "",

    @Column
    var os: String = "",

    @Column
    @Enumerated(value = EnumType.STRING)
    var platform: Platform = Platform.DESKTOP,

    @Column
    var version: String = "",

    @Column(name = "last_active_at")
    var lastActiveAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()

) : BaseEntity() {

    enum class Platform {
        DESKTOP, PHONE
    }

}