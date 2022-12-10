package io.loomus.edu.modules.user.entities.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserEntityRepository : JpaRepository<UserEntity, Int> {

    fun findByIdAndStatus(id: Int, status: UserEntity.Status): UserEntity?


    fun findByPhone(phone: String): UserEntity?


    fun findAllByPhoneIn(phones: Set<String>): List<UserEntity>


}