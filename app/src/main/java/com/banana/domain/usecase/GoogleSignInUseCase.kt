package com.banana.domain.usecase

import com.banana.domain.model.AuthResult
import com.banana.domain.model.User
import com.banana.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class GoogleSignInUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(idToken: String): Flow<AuthResult<User>> =
        authRepository.signInWithGoogle(idToken)
}
