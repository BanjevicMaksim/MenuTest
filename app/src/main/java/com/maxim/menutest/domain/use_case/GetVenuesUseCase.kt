package com.maxim.menutest.domain.use_case

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.maxim.menutest.data.remote.response.GetVenuesResponse
import com.maxim.menutest.domain.repository.MenuRepository
import com.maxim.menutest.util.ErrorData
import com.maxim.menutest.util.NoNetworkException
import com.maxim.menutest.util.Response
import com.maxim.menutest.util.ResponseInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class GetVenuesUseCase(
    private val repository: MenuRepository
) {

    suspend operator fun invoke(longitude: String, latitude: String): Response<GetVenuesResponse> =
        withContext(Dispatchers.IO) {
            try {
                Response.Success(repository.getVenues(longitude, latitude).data)
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        val type = object : TypeToken<ResponseInfo<ErrorData>>() {}.type
                        val gsonErrorData = Gson().fromJson<ResponseInfo<ErrorData>>(
                            e.response()?.errorBody()?.charStream(), type
                        )
                        Response.Error.HttpError(gsonErrorData.data)
                    }
                    is NoNetworkException -> {
                        Response.Error.NoInternetError
                    }
                    is java.lang.IllegalArgumentException -> {
                        Response.Error.BadArguments
                    }
                    else -> {
                        Response.Error.UnknownError
                    }
                }
            }
        }
}