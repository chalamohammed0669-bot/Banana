package com.banana.data.repository

import com.banana.domain.model.AuthResult
import com.banana.domain.model.User
import com.banana.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.Date

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override fun login(email: String, password: String): Flow<AuthResult<User>> = callbackFlow {
        try {
            trySend(AuthResult.Loading)
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = firebaseAuth.currentUser
                        firebaseUser?.let {
                            fetchUserFromFirestore(it.uid) { user ->
                                if (user != null) {
                                    trySend(AuthResult.Success(user))
                                } else {
                                    trySend(AuthResult.Error(Exception("User not found")))
                                }
                            }
                        }
                    } else {
                        trySend(AuthResult.Error(task.exception ?: Exception("Login failed")))
                    }
                }
        } catch (e: Exception) {
            trySend(AuthResult.Error(e))
        }
        awaitClose()
    }

    override fun signUp(
        email: String,
        password: String,
        username: String
    ): Flow<AuthResult<User>> = callbackFlow {
        try {
            trySend(AuthResult.Loading)
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = firebaseAuth.currentUser
                        firebaseUser?.let {
                            val user = User(
                                id = it.uid,
                                email = email,
                                username = username,
                                createdAt = Date(),
                                updatedAt = Date()
                            )
                            saveUserToFirestore(user) { success ->
                                if (success) {
                                    trySend(AuthResult.Success(user))
                                } else {
                                    trySend(AuthResult.Error(Exception("Failed to save user")))
                                }
                            }
                        }
                    } else {
                        trySend(AuthResult.Error(task.exception ?: Exception("Sign up failed")))
                    }
                }
        } catch (e: Exception) {
            trySend(AuthResult.Error(e))
        }
        awaitClose()
    }

    override fun signInWithGoogle(idToken: String): Flow<AuthResult<User>> = callbackFlow {
        try {
            trySend(AuthResult.Loading)
            // Implementation for Google Sign-In with Firebase
            // This requires GoogleAuthProvider credential setup
        } catch (e: Exception) {
            trySend(AuthResult.Error(e))
        }
        awaitClose()
    }

    override fun logout(): Flow<Boolean> = callbackFlow {
        try {
            firebaseAuth.signOut()
            trySend(true)
        } catch (e: Exception) {
            trySend(false)
        }
        awaitClose()
    }

    override fun resetPassword(email: String): Flow<AuthResult<Unit>> = callbackFlow {
        try {
            trySend(AuthResult.Loading)
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(AuthResult.Success(Unit))
                    } else {
                        trySend(AuthResult.Error(task.exception ?: Exception("Reset failed")))
                    }
                }
        } catch (e: Exception) {
            trySend(AuthResult.Error(e))
        }
        awaitClose()
    }

    override fun verifyEmail(): Flow<AuthResult<Unit>> = callbackFlow {
        try {
            trySend(AuthResult.Loading)
            val user = firebaseAuth.currentUser
            user?.sendEmailVerification()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(AuthResult.Success(Unit))
                    } else {
                        trySend(AuthResult.Error(task.exception ?: Exception("Verification failed")))
                    }
                }
        } catch (e: Exception) {
            trySend(AuthResult.Error(e))
        }
        awaitClose()
    }

    override fun getCurrentUser(): Flow<User?> = callbackFlow {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            fetchUserFromFirestore(firebaseUser.uid) { user ->
                trySend(user)
            }
        } else {
            trySend(null)
        }
        awaitClose()
    }

    override fun isUserLoggedIn(): Boolean = firebaseAuth.currentUser != null

    private fun saveUserToFirestore(user: User, callback: (Boolean) -> Unit) {
        firestore.collection("users")
            .document(user.id)
            .set(user)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    private fun fetchUserFromFirestore(userId: String, callback: (User?) -> Unit) {
        firestore.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)
                callback(user)
            }
            .addOnFailureListener {
                callback(null)
            }
    }
}
