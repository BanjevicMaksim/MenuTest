package com.maxim.menutest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.maxim.menutest.data.local.SharedPreferencesManager
import com.maxim.menutest.data.remote.LoginService
import com.maxim.menutest.data.remote.VenueService
import com.maxim.menutest.data.remote.response.GetVenuesResponse
import com.maxim.menutest.data.remote.response.LoginResponse
import com.maxim.menutest.data.repository.MenuRepositoryImpl
import com.maxim.menutest.domain.model.Token
import com.maxim.menutest.domain.model.VenueData
import com.maxim.menutest.domain.use_case.GetVenuesUseCase
import com.maxim.menutest.domain.use_case.LoginUseCase
import com.maxim.menutest.domain.use_case.LogoutUseCase
import com.maxim.menutest.domain.use_case.SaveUserTokenUseCase
import com.maxim.menutest.ui.login.LoginViewModel
import com.maxim.menutest.ui.venue.VenuesViewModel
import com.maxim.menutest.ui.venue_details.VenueDetailsViewModel
import com.maxim.menutest.util.ErrorData
import com.maxim.menutest.util.InfoMessage
import com.maxim.menutest.util.MenuConst.VENUE_LATITUDE
import com.maxim.menutest.util.MenuConst.VENUE_LONGITUDE
import com.maxim.menutest.util.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MenuUnitTest {

    private val venueService: VenueService = Mockito.mock(VenueService::class.java)
    private val loginService: LoginService = Mockito.mock(LoginService::class.java)
    private val preferences: SharedPreferencesManager =
        Mockito.mock(SharedPreferencesManager::class.java)

    private val repository = MenuRepositoryImpl(venueService, loginService, preferences)
    private val saveUserTokenUseCase: SaveUserTokenUseCase =
        Mockito.mock(SaveUserTokenUseCase::class.java)
    private val logoutUseCase: LogoutUseCase = Mockito.mock(LogoutUseCase::class.java)
    private val loginUseCase: LoginUseCase = Mockito.mock(LoginUseCase::class.java)
    private val getVenuesUseCase: GetVenuesUseCase = Mockito.mock(GetVenuesUseCase::class.java)

    private val loginViewModel = LoginViewModel(loginUseCase, saveUserTokenUseCase, preferences)
    private val venuesViewModel = VenuesViewModel(getVenuesUseCase)
    private val venueDetailsViewModel = VenueDetailsViewModel(logoutUseCase)

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        Dispatchers.setMain(dispatcher)
    }


    @Test
    fun `user logs in with wrong credentials, returns error`() = runTest {
        val email = "test"
        val password = "pass"

        Mockito.`when`(loginUseCase.invoke(email, password)).thenReturn(
            Response.Error.HttpError(
                ErrorData(InfoMessage("Error", "No such user"))
            )
        )

        loginViewModel.loginUser(email, password)
        assertThat(loginViewModel.ldLoginError.value?.title).isEqualTo("Error")
    }

    @Test
    fun `user logs in with right credentials, returns success`() = runTest {
        val email = "test@menutest.app"
        val password = "test1234"
        val token = "token"

        Mockito.`when`(loginUseCase.invoke(email, password)).thenReturn(
            Response.Success(
                LoginResponse(Token(token))
            )
        )

        loginViewModel.loginUser(email, password)
        assertThat(loginViewModel.ldLoginSuccess.value).isEqualTo(token)
    }

    @Test
    fun `fetch venues, returns empty list`() = runTest {
        Mockito.`when`(getVenuesUseCase.invoke(VENUE_LONGITUDE, VENUE_LATITUDE)).thenReturn(
            Response.Success(
                GetVenuesResponse(emptyList())
            )
        )

        venuesViewModel.getVenues()
        assertThat(venuesViewModel.ldVenues.value).isEqualTo(emptyList<VenueData>())
    }

    @Test
    fun `fetch venues, returns sorted list`() = runTest {
        Mockito.`when`(getVenuesUseCase.invoke(VENUE_LONGITUDE, VENUE_LATITUDE)).thenReturn(
            Response.Success(
                GetVenuesResponse(venues)
            )
        )

        venuesViewModel.getVenues()
        assertThat(venuesViewModel.ldVenues.value).isEqualTo(
            venues.sortedBy { it.distance }
        )
    }

    @Test
    fun `fetch venues, returns error`() = runTest {
        Mockito.`when`(getVenuesUseCase.invoke(VENUE_LONGITUDE, VENUE_LATITUDE)).thenReturn(
            Response.Error.UnknownError
        )

        venuesViewModel.getVenues()
        assertThat(venuesViewModel.ldVenuesError.value).isInstanceOf(Response.Error::class.java)
    }
}

val venues = listOf(
    VenueData(distance = 300.10, null),
    VenueData(distance = 200.22, null),
    VenueData(distance = 8.02, null)
)