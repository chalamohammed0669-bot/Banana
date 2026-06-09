package com.banana.domain.usecase

import com.banana.domain.model.User
import com.banana.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentUserUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(): Flow<User?> = authRepository.getCurrentUser()
}
