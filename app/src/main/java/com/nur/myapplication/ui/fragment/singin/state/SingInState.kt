package com.nur.myapplication.ui.fragment.singin.state

import com.nur.myapplication.models.auth.LoginResponseUI

sealed class SingInState {
    object Idle : SingInState()
    object Loading : SingInState()
    data class Success(val loginResponse: LoginResponseUI) : SingInState()
    data class Error(val message: String) : SingInState()
}