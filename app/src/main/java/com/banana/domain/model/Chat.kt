package com.banana.domain.model

import java.util.Date

data class Chat(
    val id: String = "",
    val participants: List<String> = emptyList(),
    val isGroupChat: Boolean = false,
    val groupName: String = "",
    val groupImageUrl: String = "",
    val lastMessage: String = "",
    val lastMessageTime: Date = Date(),
    val createdAt: Date = Date()
)
