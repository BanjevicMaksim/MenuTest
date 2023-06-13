package com.maxim.menutest

import android.app.Application
import com.maxim.menutest.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MenuApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MenuApplication)
            modules(appModule)
        }
    }
}