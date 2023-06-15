package com.maxim.menutest.ui.venue

import androidx.lifecycle.*
import com.maxim.menutest.domain.model.VenueData
import com.maxim.menutest.domain.use_case.GetVenuesUseCase
import com.maxim.menutest.util.Response
import kotlinx.coroutines.launch

class VenuesViewModel(
    private val getVenuesUseCase: GetVenuesUseCase
) : ViewModel() {

    private val _ldVenues = MutableLiveData<List<VenueData>>()
    val ldVenues: MutableLiveData<List<VenueData>>
        get() {
            return _ldVenues
        }

    val ldLoading = MutableLiveData<Boolean>()

    fun getVenues() {
        ldLoading.value = true
        viewModelScope.launch {
            getVenuesUseCase.invoke(latitude = "44.001783", longitude = "21.26907").also {
                when (it) {
                    Response.Error.NoInternetError -> {
                        // Handle no internet error
                    }
                    is Response.Success -> {
                        ldVenues.value = it.value.venues
                    }
                    else -> {
                        // Handle other cases
                    }
                }
                ldLoading.value = false
            }
        }
    }
}