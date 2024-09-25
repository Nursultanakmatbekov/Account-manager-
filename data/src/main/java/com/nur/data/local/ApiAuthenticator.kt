package com.nur.data.local

import com.nur.data.network.apiservice.SignInApiService
import com.nur.data.network.dtos.auth.AuthModelDto
import com.nur.data.network.dtos.auth.LoginResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Класс для аутентификации через API, реализующий интерфейс IServerAuthenticator
class ApiAuthenticator(private val signInApiService: SignInApiService) : IServerAuthenticator {

    // Метод для входа в систему, принимающий email и пароль
    override suspend fun signIn(email: String, password: String): String? {
        // Создаем объект AuthModelDto для отправки данных аутентификации
        val authModel = AuthModelDto(email = email, password = password)

        // Выполняем сетевой запрос в контексте IO для оптимизации производительности
        return withContext(Dispatchers.IO) {
            try {
                // Отправляем данные аутентификации и получаем ответ
                val response: LoginResponseDto = signInApiService.postAuthDataUser(authModel)
                // Возвращаем access_token из ответа
                response.access_token
            } catch (e: Exception) {
                // В случае ошибки возвращаем null
                null
            }
        }
    }
}
