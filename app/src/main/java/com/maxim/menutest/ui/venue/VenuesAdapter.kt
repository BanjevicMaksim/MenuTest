package com.maxim.menutest.ui.venue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maxim.menutest.R
import com.maxim.menutest.domain.model.VenueData
import kotlinx.android.synthetic.main.item_venue.view.*

class VenuesAdapter(
    private val venues: List<VenueData>,
    private val onVenueClick: (VenueData) -> Unit
) : RecyclerView.Adapter<VenuesAdapter.VenueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder =
        VenueViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_venue, parent, false)
        )

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        holder.bind(venues[position])
    }

    override fun getItemCount() = venues.size

    inner class VenueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(venue: VenueData) {
            with(itemView) {
                tvVenueName.text = venue.venue?.name
                tvVenueDistance.text = getAmount(venue.distance)
                tvVenueAddress.text = venue.venue?.address
                tvVenueOpen.text =
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