package com.banana.domain.repository

import com.banana.domain.model.AuthResult
import com.banana.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<AuthResult<User>>
    fun signUp(email: String, password: String, username: String): Flow<AuthResult<User>>
    fun signInWithGoogle(idToken: String): Flow<AuthResult<User>>
    fun logout(): Flow<Boolean>
    fun resetPassword(email: String): Flow<AuthResult<Unit>>
    fun verifyEmail(): Flow<AuthResult<Unit>>
    fun getCurrentUser(): Flow<User?>
    fun isUserLoggedIn(): Boolean
}
