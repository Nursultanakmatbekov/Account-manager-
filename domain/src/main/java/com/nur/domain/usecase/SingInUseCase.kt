package com.nur.domain.usecase

import com.nur.domain.repostories.auth.SingInRepository
import javax.inject.Inject

class SingInUseCase @Inject constructor(
    private val repository: SingInRepository
) {
    operator fun invoke(email: String, password: String) = repository.signIn(email, password)
}
