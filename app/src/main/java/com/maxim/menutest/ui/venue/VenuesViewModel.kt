package com.maxim.menutest.ui.venue

import androidx.lifecycle.*
import com.maxim.menutest.domain.model.VenueData
import com.maxim.menutest.domain.use_case.GetVenuesUseCase
import com.maxim.menutest.util.ErrorData
import com.maxim.menutest.util.InfoMessage
import com.maxim.menutest.util.MenuConst.VENUE_LATITUDE
import com.maxim.menutest.util.MenuConst.VENUE_LONGITUDE
import com.maxim.menutest.util.Response
import com.maxim.menutest.util.SingleLiveEvent
import kotlinx.coroutines.launch

class VenuesViewModel(
    private val getVenuesUseCase: GetVenuesUseCase
) : ViewModel() {

    private val _ldVenues = MutableLiveData<List<VenueData>>()
    val ldVenues: MutableLiveData<List<VenueData>>
        get() {
            return _ldVenues
        }

    private val _ldVenuesError = MutableLiveData<Response.Error>()
    val ldVenuesError: MutableLiveData<Response.Error>
        get() {
            return _ldVenuesError
        }

    val ldLoading = SingleLiveEvent<Boolean>()

    fun getVenues() {
        ldLoading.value = true
        viewModelScope.launch {
            getVenuesUseCase.invoke(latitude = VENUE_LATITUDE, longitude = VENUE_LONGITUDE).also {
                when (it) {
                    is Response.Error -> {
                        /***  THIS IS JUST FOR UNIT TESTING PURPOSES
                         * otherwise we would get real error and pass it to VM */
                        _ldVenuesError.value = it
                    }
                    is Response.Success -> {
                        _ldVenues.value = it.value.venues.sortedBy { venue ->
                            venue.distance
                        }
                    }
                }
                ldLoading.value = false
            }
        }
    }
}