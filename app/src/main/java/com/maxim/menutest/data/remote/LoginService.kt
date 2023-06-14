package com.maxim.menutest.data.remote

import com.maxim.menutest.data.remote.request.LoginRequest
import com.maxim.menutest.data.remote.response.LoginResponse
import com.maxim.menutest.util.ResponseInfo

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): ResponseInfo<LoginResponse>
}