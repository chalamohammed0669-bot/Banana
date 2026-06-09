package com.banana.domain.usecase

import com.banana.domain.model.AuthResult
import com.banana.domain.model.User
import com.banana.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(
        email: String,
        password: String
    ): Flow<AuthResult<User>> = authRepository.login(email, password)
}
