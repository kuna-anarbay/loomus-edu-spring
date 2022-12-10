package io.loomus.edu.modules.auth.daemon

import io.loomus.edu.modules.auth.modifiers.SmsVerificationModifier
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class SmsVerificationDaemon(
    private val modifier: SmsVerificationModifier
) {

    @Scheduled(cron = "0 10 0 * * *")
    fun deleteExpired() {
        modifier.deleteExpired()
    }

}