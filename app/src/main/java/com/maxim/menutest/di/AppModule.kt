package com.maxim.menutest.di

import com.maxim.menutest.data.local.SharedPreferencesManager
import com.maxim.menutest.data.remote.LoginService
import com.maxim.menutest.data.remote.VenueService
import com.maxim.menutest.data.repository.MenuRepositoryImpl
import com.maxim.menutest.domain.repository.MenuRepository
import com.maxim.menutest.domain.use_case.LoginUseCase
import com.maxim.menutest.domain.use_case.SaveUserTokenUseCase
import com.maxim.menutest.ui.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    single {
        fun provideVenuesService(): VenueService =
            Retrofit.Builder()
                .baseUrl("https://api-qa.menu.app/api/")
                .build()
                .create(VenueService::class.java)
    }

    single {
        fun provideLoginService(): LoginService =
            Retrofit.Builder()
                .baseUrl("https://api-qa.menu.app/api/")
                .build()
                .create(LoginService::class.java)
    }

    single {
        fun provideSharedPreferencesManager(): SharedPreferencesManager {
            return SharedPreferencesManager(androidContext())
        }
    }

    single {
        fun provideMenuRepository(): MenuRepository {
            return MenuRepositoryImpl(get(), get(), get())
        }
    }

    single {
        fun provideLoginUseCase(): LoginUseCase {
            return LoginUseCase(get())
        }
    }

    single {
        fun provideLoginUseCase(): SaveUserTokenUseCase {
            return SaveUserTokenUseCase(get())
        }
    }

    single {
        fun provideLoginViewModel(): LoginViewModel {
            return LoginViewModel(get(), get())
        }
    }
}





















