package com.nur.domain.repostories.auth

import com.nur.domain.models.auth.LoginResponse
import kotlinx.coroutines.flow.Flow

interface SingInRepository {
    fun signIn(email: String, password: String): Flow<Result<LoginResponse>>
}
