package com.maxim.menutest.data.remote

import com.maxim.menutest.data.remote.request.GetVenuesRequest
import com.maxim.menutest.data.remote.response.GetVenuesResponse
import retrofit2.http.POST

interface VenueService {

    @POST("search")
    suspend fun getVenues(request: GetVenuesRequest): GetVenuesResponse
}