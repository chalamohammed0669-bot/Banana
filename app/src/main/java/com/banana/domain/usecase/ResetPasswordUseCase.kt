package com.banana.domain.usecase

import com.banana.domain.model.AuthResult
import com.banana.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class ResetPasswordUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(email: String): Flow<AuthResult<Unit>> =
        authRepository.resetPassword(email)
}
