package com.maxim.menutest.util

import com.google.gson.annotations.SerializedName

sealed class Response<out T> {
    data class Success<R>(val value: R) : Response<R>()
    sealed class Error(open val error: ErrorData? = null) : Response<Nothing>() {
        object EmptyFieldError : Error()
        object UnknownError : Error()
        object NoInternetError : Error()
        object BadArguments : Error()

        data class HttpError(override val error: ErrorData? = null) : Error(error)
    }
}

data class ResponseInfo<out T>(
    val data: T,
    val code: Int,
    val status: String
)

class InfoMessage(
    val title: String,
    val body: String
)

data class ErrorData(
    @SerializedName("info_message")
    val infoMessage: InfoMessage,
)
