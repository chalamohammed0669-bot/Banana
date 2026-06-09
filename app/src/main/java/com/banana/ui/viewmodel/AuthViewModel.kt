package com.banana.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banana.domain.model.AuthResult
import com.banana.domain.model.User
import com.banana.domain.usecase.LoginUseCase
import com.banana.domain.usecase.SignUpUseCase
import com.banana.domain.usecase.LogoutUseCase
import com.banana.domain.usecase.ResetPasswordUseCase
import com.banana.domain.usecase.VerifyEmailUseCase
import com.banana.domain.usecase.GetCurrentUserUseCase
import com.banana.domain.usecase.GoogleSignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthResult<User>>(AuthResult.Loading)
    val authState: StateFlow<AuthResult<User>> = _authState.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _resetPasswordState = MutableStateFlow<AuthResult<Unit>>(AuthResult.Loading)
    val resetPasswordState: StateFlow<AuthResult<Unit>> = _resetPasswordState.asStateFlow()

    private val _verifyEmailState = MutableStateFlow<AuthResult<Unit>>(AuthResult.Loading)
    val verifyEmailState: StateFlow<AuthResult<Unit>> = _verifyEmailState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase(email, password).collect { result ->
                _authState.value = result
                if (result is AuthResult.Success) {
                    _currentUser.value = result.data
                }
            }
        }
    }

    fun signUp(email: String, password: String, username: String) {
        viewModelScope.launch {
            signUpUseCase(email, password, username).collect { result ->
                _authState.value = result
                if (result is AuthResult.Success) {
                    _currentUser.value = result.data
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase().collect { success ->
                if (success) {
                    _currentUser.value = null
                    _authState.value = AuthResult.Loading
                }
            }
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            resetPasswordUseCase(email).collect { result ->
                _resetPasswordState.value = result
            }
        }
    }

    fun verifyEmail() {
        viewModelScope.launch {
            verifyEmailUseCase().collect { result ->
                _verifyEmailState.value = result
            }
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase().collect { user ->
                _currentUser.value = user
            }
        }
    }

    fun googleSignIn(idToken: String) {
        viewModelScope.launch {
            googleSignInUseCase(idToken).collect { result ->
                _authState.value = result
                if (result is AuthResult.Success) {
                    _currentUser.value = result.data
                }
            }
        }
    }
}
