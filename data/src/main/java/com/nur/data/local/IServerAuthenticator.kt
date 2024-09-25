package com.nur.data.local

// Интерфейс для аутентификации через сервер
interface IServerAuthenticator {
    // Метод для входа в систему, принимающий email и пароль
    // Возвращает access_token в виде строки, или null в случае ошибки
    suspend fun signIn(email: String, password: String): String?
}
