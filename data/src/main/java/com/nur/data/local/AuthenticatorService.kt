package com.nur.data.local

import android.accounts.AccountManager
import android.app.Service
import android.content.Intent
import android.os.IBinder

// Сервис для управления аутентификацией учетных записей
class AuthenticatorService : Service() {

    // Экземпляр AccountAuthenticator для выполнения аутентификации
    private var sAccountAuthenticator: AccountAuthenticator? = null

    // Метод, вызываемый при связывании сервиса с компонентом
    override fun onBind(intent: Intent): IBinder? {
        // Проверяем, что действие соответствует аутентификатору
        return if (intent.action == AccountManager.ACTION_AUTHENTICATOR_INTENT) {
            getAuthenticator()?.iBinder // Возвращаем IBinder для взаимодействия с AccountAuthenticator
        } else {
            null // Возвращаем null, если действие не соответствует
        }
    }

    // Метод для получения экземпляра AccountAuthenticator
    private fun getAuthenticator(): AccountAuthenticator? {
        // Проверяем, существует ли уже экземпляр аутентификатора
        if (sAccountAuthenticator == null) {
            synchronized(this) { // Синхронизация для предотвращения одновременного создания экземпляра
                if (sAccountAuthenticator == null) {
                    // Инициализация нового экземпляра AccountAuthenticator
                    sAccountAuthenticator = AccountAuthenticator(this)
                }
            }
        }
        return sAccountAuthenticator // Возвращаем экземпляр аутентификатора
    }
}
