package com.maxim.menutest.ui.venue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maxim.menutest.R
import com.maxim.menutest.databinding.ItemVenueBinding
import com.maxim.menutest.domain.model.VenueData

class VenuesAdapter(
    private val venues: List<VenueData>,
    private val onVenueClick: (VenueData) -> Unit
) : RecyclerView.Adapter<VenuesAdapter.VenueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder =
        VenueViewHolder(
            ItemVenueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        holder.bind(venues[position])
    }

    override fun getItemCount() = venues.size

    inner class VenueViewHolder(private val binding: ItemVenueBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(venue: VenueData) {
            with(itemView) {
                binding.tvVenueName.text = venue.venue?.name
                binding.tvVenueDistance.text = getAmount(venue.distance)
                binding.tvVenueAddress.text = venue.venue?.address
                binding.tvVenueOpen.text =
                    if (venue.venue?.isOpen == true)
                        itemView.context.getString(R.string.Open)
                    else context.getString(R.string.Close)

                setOnClickListener {
                    onVenueClick(venue)
                }
            }
        }

        private fun getAmount(distance: Double): CharSequence {
            return if (distance > 1) "$distance km"
            else "${distance * 1000} m"
        }
    }
}