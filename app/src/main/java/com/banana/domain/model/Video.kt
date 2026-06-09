package com.banana.domain.model

import java.util.Date

data class Video(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val videoUrl: String = "",
    val thumbnailUrl: String = "",
    val duration: Long = 0,
    val musicId: String = "",
    val musicTitle: String = "",
    val filterApplied: String = "",
    val effectsApplied: List<String> = emptyList(),
    val captions: String = "",
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val sharesCount: Int = 0,
    val viewsCount: Int = 0,
    val isPublished: Boolean = false,
    val isDraft: Boolean = false,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
