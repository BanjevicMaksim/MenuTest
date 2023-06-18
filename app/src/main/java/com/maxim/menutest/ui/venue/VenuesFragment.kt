package com.maxim.menutest.ui.venue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxim.menutest.R
import com.maxim.menutest.databinding.FragmentVenuesBinding
import com.maxim.menutest.domain.model.VenueData
import com.maxim.menutest.util.InfoMessage
import com.maxim.menutest.util.MenuConst.VENUE
import com.maxim.menutest.util.hideLoader
import com.maxim.menutest.util.showError
import com.maxim.menutest.util.showLoader
import org.koin.android.ext.android.inject

class VenuesFragment : Fragment() {

    private var _binding: FragmentVenuesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VenuesViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getVenues()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVenuesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.ldVenues.observe(viewLifecycleOwner) {
            setupVenueList(it)
        }

        viewModel.ldVenuesError.observe(viewLifecycleOwner) {
            activity?.showError(
                InfoMessage(
                    getString(R.string.something_went_wrong),
                    getString(R.string.try_again)
                )
            )
        }

        viewModel.ldLoading.observe(viewLifecycleOwner) { show ->
            if (show) showLoader()
            else hideLoader()
        }
    }

    private fun setupVenueList(venues: List<VenueData>) {
        val layout = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvVenues.layoutManager = layout

        val dividerItemDecoration = DividerItemDecoration(
            binding.rvVenues.context,
            layout.orientation
        )

        ResourcesCompat.getDrawable(resources, R.drawable.divider_layer, null)
            ?.let { dividerItemDecoration.setDrawable(it) }

        binding.rvVenues.addItemDecoration(dividerItemDecoration)
        binding.rvVenues.adapter = VenuesAdapter(venues) { venue ->
            navigateToVenues(venue)
        }
    }

    private fun navigateToVenues(venueData: VenueData) {
        findNavController().navigate(
            R.id.action_venuesFragment_to_venueDetailsFragment, Bundle().apply {
                putParcelable(VENUE, venueData)
            }
        )
    }
}