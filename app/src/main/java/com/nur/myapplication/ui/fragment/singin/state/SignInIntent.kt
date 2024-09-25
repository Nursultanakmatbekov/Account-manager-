package com.nur.myapplication.ui.fragment.singin.state

import com.nur.domain.models.auth.AuthModel

sealed class SignInIntent {
    data class Submit(var email: String, var password: String) : SignInIntent()
}
