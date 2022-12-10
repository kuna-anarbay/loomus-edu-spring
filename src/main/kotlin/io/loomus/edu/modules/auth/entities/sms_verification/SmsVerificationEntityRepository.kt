package io.loomus.edu.modules.auth.entities.sms_verification

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface SmsVerificationEntityRepository : JpaRepository<SmsVerificationEntity, Int> {

    fun findByPhone(phone: String): SmsVerificationEntity?


    @Modifying
    @Query("delete from sms_verification s where s.sendAt < :expirationTime")
    fun deleteAllByExpirationTime(expirationTime: LocalDateTime)

}