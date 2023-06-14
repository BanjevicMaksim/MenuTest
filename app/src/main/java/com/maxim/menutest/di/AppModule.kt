package com.maxim.menutest.di

import com.maxim.menutest.data.local.SharedPreferencesManager
import com.maxim.menutest.data.remote.LoginService
import com.maxim.menutest.data.remote.VenueService
import com.maxim.menutest.data.repository.MenuRepositoryImpl
import com.maxim.menutest.domain.repository.MenuRepository
import com.maxim.menutest.domain.use_case.LoginUseCase
import com.maxim.menutest.domain.use_case.SaveUserTokenUseCase
import com.maxim.menutest.ui.login.LoginViewModel
import com.maxim.menutest.util.MenuConst.API_VERSION
import com.maxim.menutest.util.MenuConst.DEVICE_UUID
import com.maxim.menutest.util.MenuConst.HEADER_API_VERSION
import com.maxim.menutest.util.MenuConst.HEADER_DEVICE_UUID
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val VENUE_SERVICE = "VENUE_SERVICE"
const val LOGIN_SERVICE = "LOGIN_SERVICE"

val appModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            .addInterceptor(Interceptor { chain ->
                return@Interceptor chain.proceed(
                    chain.request().newBuilder()
                        .addHeader(HEADER_DEVICE_UUID, DEVICE_UUID)
                        .addHeader(HEADER_API_VERSION, API_VERSION)
                        .addHeader("application", "mobile-application")
                        .addHeader("Content-Type", "application/json")
                        .build()
                )
            }).build()
    }

    single<VenueService>(named(VENUE_SERVICE)) {
        Retrofit.Builder()
            .client(get())
            .baseUrl("https://api-qa.menu.app/api/directory/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VenueService::class.java)
    }

    single<LoginService>(named(LOGIN_SERVICE)) {
        Retrofit.Builder()
            .client(get())
            .baseUrl("https://api-qa.menu.app/api/customers/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginService::class.java)
    }

    single {
        SharedPreferencesManager(androidContext())
    }

    single<MenuRepository> {
        MenuRepositoryImpl(get(named(VENUE_SERVICE)), get(named(LOGIN_SERVICE)), get())
    }

    single {
        LoginUseCase(get())
    }

    single {
        SaveUserTokenUseCase(get())
    }

    single {
        LoginViewModel(get(), get())
    }
}





















