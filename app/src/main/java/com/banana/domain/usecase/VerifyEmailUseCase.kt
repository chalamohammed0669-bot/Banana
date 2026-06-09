package com.banana.domain.usecase

import com.banana.domain.model.AuthResult
import com.banana.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class VerifyEmailUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(): Flow<AuthResult<Unit>> = authRepository.verifyEmail()
}
