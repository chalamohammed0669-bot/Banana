package com.banana.domain.model

import java.util.Date

data class LiveStream(
    val id: String = "",
    val creatorId: String = "",
    val title: String = "",
    val description: String = "",
    val streamUrl: String = "",
    val thumbnailUrl: String = "",
    val viewerCount: Int = 0,
    val likeCount: Int = 0,
    val status: String = "", // "live", "ended", "scheduled"
    val startTime: Date = Date(),
    val endTime: Date? = null,
    val categoryTags: List<String> = emptyList()
)
