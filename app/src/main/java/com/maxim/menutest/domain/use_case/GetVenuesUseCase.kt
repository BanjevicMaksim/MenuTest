package com.maxim.menutest.domain.use_case

import com.maxim.menutest.data.remote.response.GetVenuesResponse
import com.maxim.menutest.domain.repository.MenuRepository
import com.maxim.menutest.util.Response

class GetVenuesUseCase(
    private val repository: MenuRepository
) {

    suspend operator fun invoke(longitude: String, latitude: String): Response<GetVenuesResponse> {
        return repository.getVenues(longitude, latitude)
    }
}