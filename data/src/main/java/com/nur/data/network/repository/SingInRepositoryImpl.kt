package com.nur.data.network.repository

import com.nur.data.local.IServerAuthenticator
import com.nur.data.network.apiservice.SignInApiService
import com.nur.data.network.dtos.auth.AuthModelDto
import com.nur.data.network.dtos.auth.toDomain
import com.nur.domain.models.auth.LoginResponse
import com.nur.domain.repostories.auth.SingInRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SingInRepositoryImpl @Inject constructor(
    private val apiService: SignInApiService
) : SingInRepository {

    override fun signIn(email: String, password: String): Flow<Result<LoginResponse>> = flow {
        try {
            val response = apiService.postAuthDataUser(
                AuthModelDto(email = email, password = password)
            ).toDomain()
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
