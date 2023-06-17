package com.maxim.menutest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.maxim.menutest.data.local.SharedPreferencesManager
import com.maxim.menutest.data.remote.LoginService
import com.maxim.menutest.data.remote.VenueService
import com.maxim.menutest.data.remote.response.GetVenuesResponse
import com.maxim.menutest.data.remote.response.LoginResponse
import com.maxim.menutest.data.repository.MenuRepositoryImpl
import com.maxim.menutest.domain.model.Token
import com.maxim.menutest.domain.use_case.GetVenuesUseCase
import com.maxim.menutest.domain.use_case.LoginUseCase
import com.maxim.menutest.domain.use_case.LogoutUseCase
import com.maxim.menutest.util.MenuConst.VENUE_LATITUDE
import com.maxim.menutest.util.MenuConst.VENUE_LONGITUDE
import com.maxim.menutest.util.ResponseInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MenuUseCaseUnitTest {

    private val venueService: VenueService = Mockito.mock(VenueService::class.java)
    private val loginService: LoginService = Mockito.mock(LoginService::class.java)
    private val preferences: SharedPreferencesManager =
        Mockito.mock(SharedPreferencesManager::class.java)

    private val repository = MenuRepositoryImpl(venueService, loginService, preferences)

    private val logoutUseCase: LogoutUseCase = Mockito.mock(LogoutUseCase::class.java)
    private val loginUseCase: LoginUseCase = LoginUseCase(repository)
    private val getVenuesUseCase: GetVenuesUseCase = GetVenuesUseCase(repository)

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun init() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `user logs in with wrong credentials, returns error`() = runTest {
        val email = "test"
        val password = "pass"

        Mockito.`when`(repository.loginUser(email, password)).thenThrow(
            HttpException(
                Response.error<String>(401, error_401_content.toResponseBody())
            )
        )

        Truth.assertThat(loginUseCase.invoke(email, password))
            .isInstanceOf(com.maxim.menutest.util.Response.Error.HttpError::class.java)
    }

    @Test
    fun `user logs in with right credentials, returns success`() = runTest {
        val email = "test@menutest.app"
        val password = "test1234"
        val token = "token"
        val info = ResponseInfo(
            LoginResponse(
                Token(token)
            ), 200, "Success"
        )


        Mockito.`when`(repository.loginUser(email, password)).thenReturn(info)

        Truth.assertThat(loginUseCase.invoke(email, password))
            .isInstanceOf(com.maxim.menutest.util.Response.Success::class.java)
    }

    @Test
    fun `fetch venues wrong arguments, returns error`() = runTest {
        Mockito.`when`(repository.getVenues(VENUE_LONGITUDE, VENUE_LATITUDE)).thenThrow(
            java.lang.IllegalArgumentException()
        )

        Truth.assertThat(getVenuesUseCase.invoke(VENUE_LONGITUDE, VENUE_LATITUDE))
            .isInstanceOf(com.maxim.menutest.util.Response.Error.BadArguments::class.java)
    }

    @Test
    fun `fetch venues right arguments, returns success`() = runTest {
        Mockito.`when`(repository.getVenues(VENUE_LONGITUDE, VENUE_LATITUDE)).thenReturn(
            ResponseInfo(GetVenuesResponse(venues), 200, "Success")
        )

        Truth.assertThat(getVenuesUseCase.invoke(VENUE_LONGITUDE, VENUE_LATITUDE))
            .isInstanceOf(com.maxim.menutest.util.Response.Error.BadArguments::class.java)
    }
}

const val error_401_content =
    "{\"status\":\"Consumer Error\",\"code\":1000,\"data\":{\"info_message\":{\"title\":\"That didn't work\",\"body\":\"Credentials are incorrect. Double-check and try again.\"},\"message\":\"Consumer Error\",\"line\":102,\"file\":\"\\/var\\/app\\/current\\/vendor\\/menu\\/core\\/src\\/Entities\\/CustomerAccount\\/Handlers\\/CustomerAccountFromLoginRequest.php\",\"error_id\":\"\"}}"