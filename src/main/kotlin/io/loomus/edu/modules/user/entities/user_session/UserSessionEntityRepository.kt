package io.loomus.edu.modules.user.entities.user_session

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface UserSessionEntityRepository : JpaRepository<UserSessionEntity, Int> {

    fun findAllByUserId(userId: Int): List<UserSessionEntity>


    @Modifying
    @Query("delete from user_session s where s.lastActiveAt < :expirationTime")
    fun deleteExpired(expirationTime: LocalDateTime)


    fun deleteAllByUserId(userId: Int)

}