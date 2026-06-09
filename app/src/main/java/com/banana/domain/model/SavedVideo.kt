package com.banana.domain.model

import java.util.Date

data class SavedVideo(
    val id: String = "",
    val userId: String = "",
    val videoId: String = "",
    val createdAt: Date = Date()
)
