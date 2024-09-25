package com.nur.data.local

// Утилитарный объект для работы с аутентификацией учетной записи
object AccountUtils {

    // Интерфейс для аутентификации через API
    private lateinit var apiAuthenticator: IServerAuthenticator

    // Метод инициализации для установки экземпляра IServerAuthenticator
    fun initialize(apiAuthenticator: IServerAuthenticator) {
        this.apiAuthenticator = apiAuthenticator // Сохраняем переданный экземпляр aпликуемого аутентификатора
    }

    // Метод для выполнения аутентификации пользователя
    suspend fun signIn(email: String, password: String): String? {
        return apiAuthenticator.signIn(email, password) // Вызываем метод signIn у экземпляра IServerAuthenticator
    }
}
