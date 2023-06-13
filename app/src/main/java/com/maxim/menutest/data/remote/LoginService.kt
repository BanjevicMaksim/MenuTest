package com.maxim.menutest.data.remote

import com.maxim.menutest.data.remote.request.LoginRequest
import com.maxim.menutest.data.remote.response.LoginResponse
import com.maxim.menutest.util.Response
import retrofit2.http.POST

interface LoginService {

    @POST("customers/login")
    suspend fun login(request: LoginRequest): Response<LoginResponse>
}