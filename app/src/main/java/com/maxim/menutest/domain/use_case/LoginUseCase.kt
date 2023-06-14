package com.maxim.menutest.domain.use_case

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.maxim.menutest.data.remote.response.LoginResponse
import com.maxim.menutest.domain.repository.MenuRepository
import com.maxim.menutest.util.ErrorData
import com.maxim.menutest.util.Response
import com.maxim.menutest.util.ResponseInfo
import retrofit2.HttpException

class LoginUseCase(
    private val repository: MenuRepository
) {

    suspend operator fun invoke(email: String, password: String): Response<LoginResponse> {
        if (email.isEmpty()) return Response.Error()

        try {
            return Response.Success(repository.loginUser(email, password).data)
        } catch (e: Exception) {
            return when (e) {
                is HttpException -> {
                    val type = object : TypeToken<ResponseInfo<ErrorData>>() {}.type
                    val gsonErrorData = Gson().fromJson<ResponseInfo<ErrorData>>(
                        e.response()?.errorBody()?.charStream(), type
                    )
                    Response.Error(gsonErrorData.data)
                }
                else -> {
                    Response.Error()
                }
            }
        }
    }
}