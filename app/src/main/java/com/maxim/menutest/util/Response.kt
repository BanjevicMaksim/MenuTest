package com.maxim.menutest.util

sealed class Response<T : Any?>(
    val data: T? = null
) {
    class Loading<T : Any?>(data: T? = null) : Response<T>(data)
    class Success<T : Any?>(data: T) : Response<T>(data)

    class Error<T : Any?>(message: String, val cause: Exception? = null, data: T? = null) :
        Response<T>(data)

}