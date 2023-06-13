package com.maxim.menutest.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

object ApiUtil {

    suspend fun <T> executeApiCall(
        apiCall: suspend () -> T
    ): Response<T> {
        return withContext(Dispatchers.IO) {
            try {
                Response.Success(apiCall.invoke())
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> Response.Error(e.message(), e)
                    else -> Response.Error(e.message ?: "Unknown error")
                }
            }
        }
    }
}