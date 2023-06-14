package com.maxim.menutest.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

//suspend fun <T> executeApiCall(
//    apiCall: suspend () -> ResponseInfo<T>
//): Response<ResponseInfo<T>> {
//    return runBlocking {
//        try {
////            Response.Success(apiCall.invoke())
//        } catch (e: Exception) {
//            when (e) {
//                is HttpException -> {
//                    val type = object : TypeToken<ResponseInfo<ErrorData>>() {}.type
//                    val body = Gson().fromJson<ResponseInfo<ErrorData>>(e.response()?.errorBody()?.charStream(), type)
//                    println(body)
//                    Response.Error<ErrorData>()
//                }
////                else -> Response.Error(e.message ?: "")
//            }
//        }
//    }
//}