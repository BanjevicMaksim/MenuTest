package com.maxim.menutest.data.repository

import com.maxim.menutest.data.local.SharedPreferencesManager
import com.maxim.menutest.data.remote.LoginService
import com.maxim.menutest.data.remote.VenueService
import com.maxim.menutest.domain.repository.MenuRepository
import com.maxim.menutest.data.remote.request.LoginRequest
import com.maxim.menutest.data.remote.response.LoginResponse
import com.maxim.menutest.data.remote.request.GetVenuesRequest
import com.maxim.menutest.data.remote.response.GetVenuesResponse
import com.maxim.menutest.util.ApiUtil
import com.maxim.menutest.util.Response

/**
 * This should be represented ideally as two separate repos for
 * users and another for venues, but for simplicity reasons
 * I used one.
 */
class MenuRepositoryImpl(
    private val venuesApi: VenueService,
    private val loginService: LoginService,
    private val preferencesManager: SharedPreferencesManager
) : MenuRepository {

    override suspend fun getVenues(
        longitude: String,
        latitude: String
    ): Response<GetVenuesResponse> =
        ApiUtil.executeApiCall {
            venuesApi.getVenues(
                GetVenuesRequest(
                    latitude, longitude
                )
            )
        }

    override suspend fun loginUser(
        username: String,
        password: String
    ): Response<LoginResponse> =
        loginService.login(
            LoginRequest(
                username, password
            )
        )

    override suspend fun removeUserToken() {
        preferencesManager.removeUserToken()
    }

    override fun saveUserToken(token: String) {
        preferencesManager.saveUserToken(token)
    }
}