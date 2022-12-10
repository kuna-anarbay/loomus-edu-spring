package io.loomus.edu.modules.user.entities.user_avatar

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAvatarEntityRepository : JpaRepository<UserAvatarEntity, Int> {

    fun findAllByUserIdIn(userIds: Set<Int>): List<UserAvatarEntity>

}