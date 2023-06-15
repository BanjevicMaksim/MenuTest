package com.maxim.menutest.domain.repository

import com.maxim.menutest.data.remote.response.GetVenuesResponse
import com.maxim.menutest.data.remote.response.LoginResponse
import com.maxim.menutest.util.ResponseInfo

/**
 * This should be represented ideally as two separate repos for
 * users and another for venues, but for simplicity reasons
 * I used one.
 */
interface MenuRepository {

    suspend fun getVenues(longitude: String, latitude: String): ResponseInfo<GetVenuesResponse>

    suspend fun loginUser(username: String, password: String): ResponseInfo<LoginResponse>

    suspend fun removeUserToken()

    fun saveUserToken(token: String)
}