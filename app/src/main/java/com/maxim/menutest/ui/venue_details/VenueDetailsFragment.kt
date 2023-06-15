package com.maxim.menutest.ui.venue_details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.maxim.menutest.R
import com.maxim.menutest.domain.model.VenueData
import com.maxim.menutest.util.MenuConst.VENUE
import kotlinx.android.synthetic.main.fragment_venue_details.*
import org.koin.android.ext.android.inject

class VenueDetailsFragment: Fragment() {

    private val viewModel: VenueDetailsViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_venue_details, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLiveData()
        btnLogout.setOnClickListener { viewModel.logoutUser() }
        setupViews()
    }

    private fun setupViews() {
        val venueData = arguments?.getParcelable<VenueData>(VENUE)
        tvVenueDetailsDescription.text = venueData?.venue?.description
        tvVenueDetailsName.text = venueData?.venue?.name
        tvVenueDetailsWelcome.text = venueData?.venue?.welcomeMessage
        tvVenueDetailsOpen.text = venueData?.venue?.isOpen.toString()

        Glide.with(requireContext()).load(venueData?.venue?.image?.thumbnail).into(ivVenueImage)
    }

    private fun observeLiveData() {
        viewModel.ldLogoutSuccess.observe(viewLifecycleOwner) {
            activity?.findNavController(R.id.fcvNavContainer)?.navigate(R.id.action_venueDetailsFragment_to_loginFragment)
        }
    }
}