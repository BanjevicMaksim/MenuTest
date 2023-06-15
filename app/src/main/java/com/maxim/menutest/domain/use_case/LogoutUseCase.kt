package com.maxim.menutest.domain.use_case

import com.maxim.menutest.data.local.SharedPreferencesManager

class LogoutUseCase(
    private val sharedPreferencesManager: SharedPreferencesManager
) {

    operator fun invoke() {
        sharedPreferencesManager.removeUserToken()
    }
}