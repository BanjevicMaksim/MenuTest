package com.maxim.menutest.ui.venue_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maxim.menutest.domain.use_case.LogoutUseCase

class VenueDetailsViewModel(
    private val logoutUseCase: LogoutUseCase
): ViewModel() {

    private val _ldLogoutSuccess = MutableLiveData<Boolean>()
    val ldLogoutSuccess: MutableLiveData<Boolean>
        get() {
            return _ldLogoutSuccess
        }

    fun logoutUser() {
        logoutUseCase.invoke()
        _ldLogoutSuccess.value = true
    }
}