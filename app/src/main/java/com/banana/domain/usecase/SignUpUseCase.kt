package com.banana.domain.usecase

import com.banana.domain.model.AuthResult
import com.banana.domain.model.User
import com.banana.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class SignUpUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(
        email: String,
        password: String,
        username: String
    ): Flow<AuthResult<User>> = authRepository.signUp(email, password, username)
}
