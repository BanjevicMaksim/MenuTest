package com.maxim.menutest.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxim.menutest.domain.use_case.LoginUseCase
import com.maxim.menutest.domain.use_case.SaveUserTokenUseCase
import com.maxim.menutest.util.Response
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val saveUserTokenUseCase: SaveUserTokenUseCase
): ViewModel() {

    private val _ldLoginSuccess = MutableLiveData<String>()
    val ldLoginSuccess: MutableLiveData<String>
        get() { return _ldLoginSuccess }

    private val _ldLoginError = MutableLiveData<String>()
    val ldLoginError: MutableLiveData<String>
        get() { return _ldLoginError }

    val ldLoading = MutableLiveData<Boolean>()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase.invoke(email, password).also {
                when (it) {
                    is Response.Error -> {
                        _ldLoginError.value = it.cause?.message
                    }
                    is Response.Loading -> {
                        ldLoading.value = true
                    }
                    is Response.Success -> {
                        _ldLoginSuccess.value = it.data?.token
                    }
                }
            }

        }
    }

    fun saveUserToken(token: String) {
        saveUserTokenUseCase.invoke(token)
    }
}