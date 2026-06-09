package com.banana.domain.usecase

import com.banana.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class LogoutUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(): Flow<Boolean> = authRepository.logout()
}
