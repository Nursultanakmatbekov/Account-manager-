package com.nur.data.local

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.accounts.NetworkErrorException
import android.content.Context
import android.os.Bundle
import kotlinx.coroutines.runBlocking

// Класс, отвечающий за аутентификацию аккаунтов
class AccountAuthenticator(private val mContext: Context) : AbstractAccountAuthenticator(mContext) {

    // Метод для добавления нового аккаунта
    override fun addAccount(
        response: AccountAuthenticatorResponse,
        accountType: String,
        authTokenType: String?,
        requiredFeatures: Array<String>?,
        options: Bundle?
    ): Bundle {
        val reply = Bundle()
        // Реализовать логику для добавления аккаунта при необходимости
        return reply
    }

    // Метод для подтверждения учетных данных аккаунта
    override fun confirmCredentials(
        response: AccountAuthenticatorResponse,
        account: Account,
        options: Bundle?
    ): Bundle? {
        // Возвращает null, если не реализована логика подтверждения
        return null
    }

    // Метод для редактирования свойств аккаунта
    override fun editProperties(
        response: AccountAuthenticatorResponse,
        accountType: String
    ): Bundle? {
        // Возвращает null, если редактирование свойств не поддерживается
        return null
    }

    // Метод для получения токена аутентификации
    override fun getAuthToken(
        response: AccountAuthenticatorResponse,
        account: Account,
        authTokenType: String,
        options: Bundle?
    ): Bundle {
        val am = AccountManager.get(mContext) // Получаем экземпляр AccountManager
        var authToken = am.peekAuthToken(account, authTokenType) // Получаем токен, если он существует

        // Если токен пуст, пытаемся получить пароль и аутентифицироваться
        if (authToken.isNullOrEmpty()) {
            val password = am.getPassword(account)
            if (password != null) {
                runBlocking {
                    // Вызов метода для входа и получения токена
                    authToken = AccountUtils.signIn(account.name, password)
                    if (!authToken.isNullOrEmpty()) {
                        // Сохраняем токен в AccountManager
                        am.setAuthToken(account, authTokenType, authToken)
                    }
                }
            }
        }

        // Возвращаем Bundle с информацией о токене или сообщение об ошибке
        return if (!authToken.isNullOrEmpty()) {
            Bundle().apply {
                putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
                putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
                putString(AccountManager.KEY_AUTHTOKEN, authToken)
            }
        } else {
            Bundle().apply {
                putString(AccountManager.KEY_ERROR_MESSAGE, "Не удалось получить токен аутентификации")
            }
        }
    }

    // Метод для получения метки токена аутентификации
    override fun getAuthTokenLabel(authTokenType: String): String {
        // Возвращает метку для типа токена
        return "Token for type: $authTokenType"
    }

    @Throws(NetworkErrorException::class)
    // Метод для проверки наличия функциональных возможностей у аккаунта
    override fun hasFeatures(
        response: AccountAuthenticatorResponse,
        account: Account,
        features: Array<String>
    ): Bundle? {
        // Возвращает null, если функциональные возможности не поддерживаются
        return null
    }

    @Throws(NetworkErrorException::class)
    // Метод для обновления учетных данных аккаунта
    override fun updateCredentials(
        response: AccountAuthenticatorResponse,
        account: Account,
        authTokenType: String?,
        options: Bundle?
    ): Bundle? {
        // Возвращает null, если обновление учетных данных не поддерживается
        return null
    }
}
