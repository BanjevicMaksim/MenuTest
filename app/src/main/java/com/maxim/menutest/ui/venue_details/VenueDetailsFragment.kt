package com.maxim.menutest.ui.venue_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.maxim.menutest.R
import com.maxim.menutest.databinding.FragmentVenueDetailsBinding
import com.maxim.menutest.domain.model.VenueData
import com.maxim.menutest.util.MenuConst.VENUE
import org.koin.android.ext.android.inject

class VenueDetailsFragment : Fragment() {

    private var _binding: FragmentVenueDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VenueDetailsViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVenueDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLiveData()
        setupViews()
    }

    private fun setupViews() {
        val venueData = arguments?.getParcelable<VenueData>(VENUE)
        binding.tvVenueDetailsDescription.text = venueData?.venue?.description
        binding.tvVenueDetailsName.text = venueData?.venue?.name
        binding.tvVenueDetailsWelcome.text = venueData?.venue?.welcomeMessage
        binding.tvVenueDetailsOpen.text = venueData?.venue?.isOpen.toString()

        Glide.with(requireContext())
            .load(venueData?.venue?.image?.thumbnail)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivVenueImage)

        binding.btnLogout.setOnClickListener { viewModel.logoutUser() }
    }

    private fun observeLiveData() {
        viewModel.ldLogoutSuccess.observe(viewLifecycleOwner) {
            activity?.findNavController(R.id.fcvNavContainer)
                ?.navigate(R.id.action_venueDetailsFragment_to_loginFragment)
        }
    }
}