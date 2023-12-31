package com.maxim.menutest.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPreferencesManager(context: Context) {

    private var sharedPreferences = context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)

    fun saveUserToken(token: String) {
        setUserToken(token)
    }

    fun removeUserToken() {
        setUserToken("")
    }

    private fun setUserToken(token: String) {
        sharedPreferences.edit().apply {
            putString(USER_TOKEN, token)
            apply()
        }
    }

    fun isUserLoggedIn() = sharedPreferences.getString(USER_TOKEN, "") != ""

    companion object {
        private const val PREFERENCES = "PREFERENCES"
        private const val USER_TOKEN = "USER_TOKEN"
    }
}