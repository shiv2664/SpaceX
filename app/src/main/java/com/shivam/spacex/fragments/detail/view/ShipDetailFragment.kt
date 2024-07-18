package com.shivam.spacex.fragments.detail.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shivam.spacex.databinding.FragmentShipDetailBinding
import com.shivam.spacex.databinding.FragmentShipListingBinding
import com.shivam.spacex.fragments.detail.viewmodel.DetailsViewModel
import com.shivam.spacex.fragments.listing.adapter.ShipListAdapter
import com.shivam.spacex.fragments.listing.viewmodel.ShipsListingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class ShipDetailFragment : Fragment() {

    private var _binding: FragmentShipDetailBinding?=null
    private val binding get()=_binding!!

    private val shipListListViewModel : DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShipDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val flightNumber = arguments?.getInt("flightNumber")

        if (flightNumber != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val item= shipListListViewModel.getDetails(flightNumber)
                withContext(Dispatchers.Main){
                    binding.missionName.text=item?.mission_name
                    binding.launchDate.text=item?.launch_date_local
                    binding.launchSite.text=item?.launch_site?.site_name
                    binding.rocketName.text = item?.rocket?.rocket_name
                    binding.rockettype.text = item?.rocket?.rocket_type
                    binding.payloadId.text = item?.rocket?.second_stage?.payloads?.get(0)?.payload_id
                    binding.payloadCountry.text = item?.rocket?.second_stage?.payloads?.get(0)?.payload_mass_kg.toString()
                    binding.link.text = item?.links?.article_link


                }

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
