package com.maxim.menutest.data.remote.response

import com.maxim.menutest.domain.model.VenueData

data class GetVenuesResponse(
    val venues: List<VenueData>
)
