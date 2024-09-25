package com.nur.data.network.di.interceptor

import android.accounts.AccountManager
import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

// Интерсептор для добавления токена аутентификации в HTTP-запросы
class TokenInterceptor @Inject constructor(
    private val context: Context // Внедрение контекста приложения
) : Interceptor {

    // Метод перехвата запросов
    override fun intercept(chain: Interceptor.Chain): Response {
        // Получение оригинального HTTP-запроса
        val request = chain.request()

        // Получение экземпляра AccountManager для управления учетными записями
        val accountManager = AccountManager.get(context)

        // Получение всех учетных записей типа "My Application"
        val accounts = accountManager.getAccountsByType("My Application")

        // Извлечение токена аутентификации из первой найденной учетной записи
        val token = accounts.firstOrNull()?.let { account ->
            accountManager.peekAuthToken(account, "full_access") // Попытка получить токен
        }

        // Создание нового запроса с заголовком Authorization, если токен доступен
        val newRequest = token?.let {
            request.newBuilder()
                .addHeader("Authorization", "Bearer $it") // Добавление заголовка с токеном
                .build() // Создание нового запроса
        } ?: request // Если токен отсутствует, используем оригинальный запрос

        // Выполнение запроса с модифицированными заголовками
        return chain.proceed(newRequest)
    }
}
