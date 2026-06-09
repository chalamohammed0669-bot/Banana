package com.banana.domain.model

import java.util.Date

data class Call(
    val id: String = "",
    val callerId: String = "",
    val receiverId: String = "",
    val callType: String = "", // "audio" or "video"
    val status: String = "", // "incoming", "outgoing", "active", "ended", "missed"
    val duration: Long = 0,
    val startTime: Date = Date(),
    val endTime: Date? = null
)
