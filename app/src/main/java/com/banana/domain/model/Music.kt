package com.banana.domain.model

data class Music(
    val id: String = "",
    val title: String = "",
    val artist: String = "",
    val duration: Long = 0,
    val audioUrl: String = "",
    val coverUrl: String = "",
    val usageCount: Int = 0
)
