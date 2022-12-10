package io.loomus.edu.modules.auth.entities.sms_verification

import io.loomus.edu.utils.BaseEntity
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "sms_verification")
class SmsVerificationEntity(

    @Column(unique = true)
    val phone: String = "",

    @Column
    var codes: String = "",

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "last_send_at")
    var sendAt: LocalDateTime = LocalDateTime.now()

) : BaseEntity()