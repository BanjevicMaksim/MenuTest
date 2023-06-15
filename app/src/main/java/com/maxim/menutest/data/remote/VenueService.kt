package com.maxim.menutest.data.remote

import com.maxim.menutest.data.remote.request.GetVenuesRequest
import com.maxim.menutest.data.remote.response.GetVenuesResponse
import com.maxim.menutest.util.ResponseInfo
import retrofit2.http.Body
import retrofit2.http.POST

interface VenueService {

    @POST("search")
    suspend fun getVenues(@Body request: GetVenuesRequest): ResponseInfo<GetVenuesResponse>
}