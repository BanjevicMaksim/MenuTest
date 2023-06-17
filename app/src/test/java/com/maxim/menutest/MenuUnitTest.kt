package com.maxim.menutest

import com.maxim.menutest.data.remote.LoginService
import com.maxim.menutest.data.remote.VenueService
import com.maxim.menutest.domain.use_case.SaveUserTokenUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MenuUnitTest {

    lateinit var saveUserTokenUseCase: SaveUserTokenUseCase

    val venueService: VenueService = Mockito.mock(VenueService::class.java)
    val loginService: LoginService = Mockito.mock(LoginService::class.java)

    @Before
    fun init() {
        saveUserTokenUseCase = Mockito.mock(SaveUserTokenUseCase::class.java)
    }

    @Test
    fun test_saveUserToken_useCase() {

    }
}