package com.shivam.spacex.fragments.listing.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.shivam.spacex.databinding.ItemShipListingBinding
import com.shivam.spacex.fragments.listing.model.XModel

class ShipListAdapter(private val listener: ShipListListener):PagingDataAdapter<XModel,RecyclerView.ViewHolder>(
    Diff()
) {

    private var context: Context?=null

    fun setContext(context: Context){
        this.context=context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = ItemShipListingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListingItemViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val listingItemViewHolder = holder as ListingItemViewHolder
        val item = getItem(position)

        listingItemViewHolder.binding.let {
            it.missionName.text=item?.mission_name
            it.launchYear.text=item?.launch_year
            it.rocketName.text=item?.rocket?.rocket_name
            it.favorite.isSelected= false

            it.favorite.setOnClickListener {
                listener.onBookmarkClick(item?.flight_number, listingItemViewHolder.binding.favorite,item)
            }
        }

        listener.isBookmark(item?.flight_number, listingItemViewHolder.binding.favorite)

        listingItemViewHolder.itemView.setOnClickListener {
            if (item != null) {
                listener.onItemClick(item.flight_number)
            }
        }





    }

    class Diff: ItemCallback<XModel>(){
        override fun areItemsTheSame(oldItem: XModel, newItem: XModel): Boolean {
            return oldItem.flight_number==newItem.flight_number
        }

        override fun areContentsTheSame(oldItem: XModel, newItem: XModel): Boolean {
            return oldItem==newItem
        }
    }

    interface ShipListListener{
        fun onItemClick(item: Int?)
        fun onBookmarkClick(item: Int?, favorite: ImageView, item1: XModel?)
        fun isBookmark(item: Int?, favorite: ImageView)
    }

    inner class ListingItemViewHolder( val binding:ItemShipListingBinding):RecyclerView.ViewHolder(binding.root)

}