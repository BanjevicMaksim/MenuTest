package com.maxim.menutest.domain.use_case

import com.maxim.menutest.data.remote.response.LoginResponse
import com.maxim.menutest.domain.repository.MenuRepository
import com.maxim.menutest.util.Response

class LoginUseCase(
    private val repository: MenuRepository
) {

    suspend operator fun invoke(email: String, password: String): Response<LoginResponse> {
        if (email.isEmpty()) return Response.Error(cause = IllegalArgumentException(), message = "")
        return repository.loginUser(email, password)
    }
}