package io.loomus.edu.modules.user.daemon

import io.loomus.edu.modules.user.modifiers.UserSessionModifier
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserSessionDaemon(
    private val modifier: UserSessionModifier
) {

    @Scheduled(cron = "0 15 6 * * *")
    fun deleteExpired() {
        modifier.deleteExpired()
    }

}