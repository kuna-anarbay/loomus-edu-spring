package io.loomus.edu.extensions

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

// Converts to date with UTC+0
val Int.dateTime: LocalDateTime
    get() {
        return Instant.ofEpochSecond(this.toLong()).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }


// Converts to second with UTC+0
val LocalDateTime.seconds: Int
    get() {
        return this.toEpochSecond(ZoneOffset.ofHours(6)).toInt()
    }