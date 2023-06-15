package com.maxim.menutest.ui.venue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxim.menutest.R
import com.maxim.menutest.domain.model.VenueData
import com.maxim.menutest.ui.MainActivity
import com.maxim.menutest.util.MenuConst.VENUE
import kotlinx.android.synthetic.main.fragment_venues.*
import org.koin.android.ext.android.inject


class VenuesFragment : Fragment() {

    private val viewModel: VenuesViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getVenues()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_venues, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLiveData()
    }


    private fun observeLiveData() {
        viewModel.ldVenues.observe(viewLifecycleOwner) { venues ->
            setupVenueList(venues)
        }

        viewModel.ldLoading.observe(viewLifecycleOwner) { show ->
            (activity as MainActivity).run {
                if (show) showLoader()
                else hideLoader()
            }
        }
    }

    private fun setupVenueList(venues: List<VenueData>) {
        val layout = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvVenues.layoutManager = layout

        val dividerItemDecoration = DividerItemDecoration(
            rvVenues.context,
            layout.orientation
        )
        rvVenues.addItemDecoration(dividerItemDecoration)
        rvVenues.adapter = VenuesAdapter(venues) { venue ->
            navigateToVenues(venue)
        }
    }

    private fun navigateToVenues(venueData: VenueData) {
        activity?.findNavController(R.id.fcvNavContainer)?.navigate(
            R.id.action_venuesFragment_to_venueDetailsFragment, Bundle().apply {
                putParcelable(VENUE, venueData)
            }
        )
    }
}