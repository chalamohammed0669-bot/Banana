package com.banana.domain.model

import java.util.Date

data class User(
    val id: String = "",
    val email: String = "",
    val username: String = "",
    val profileImageUrl: String = "",
    val bio: String = "",
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val likesCount: Int = 0,
    val isVerified: Boolean = false,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
