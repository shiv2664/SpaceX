package com.shivam.spacex.fragments.listing.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shivam.spacex.R
import com.shivam.spacex.fragments.listing.adapter.ShipListAdapter
import com.shivam.spacex.fragments.listing.viewmodel.ShipsListingViewModel
import com.shivam.spacex.databinding.FragmentShipListingBinding
import com.shivam.spacex.fragments.listing.model.BookMarkModel
import com.shivam.spacex.fragments.listing.model.XModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ShipListingFragment : Fragment() {

    private var _binding: FragmentShipListingBinding?=null
    private val binding get()=_binding!!

    private val movieListViewModel : ShipsListingViewModel by viewModels()
    private var moviesListAdapter: ShipListAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShipListingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesListAdapter= ShipListAdapter(object : ShipListAdapter.ShipListListener{

            override fun onItemClick(item: Int?) {

                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.shipListingFragment, inclusive = false) // Optional: Pop up to the starting destination
                    .setLaunchSingleTop(true) // Optional: Prevent multiple instances of the same destination
                    .build()

                val bundle =Bundle()
                if (item != null) {
                    bundle.putInt("flightNumber",item)
                }

                findNavController().navigate(R.id.action_shipListingFragment_to_shipDetailFragment,bundle,navOptions)



            }

            override fun onBookmarkClick(item: Int?, favorite: ImageView, item1: XModel?) {

                CoroutineScope(Dispatchers.IO).launch {
                    val checkBookmark = item?.let { movieListViewModel.getBookmark(it) }
                    withContext(Dispatchers.Main){
                        if (checkBookmark!=null){
                            movieListViewModel.deleteBookmark(checkBookmark)
                            favorite.isSelected=false
                        }else{
                            if (item != null) {
                                val bookMarkModel = BookMarkModel(item1?.flight_number,item1?.details,item1?.is_tentative,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null)
                                movieListViewModel.insertBookmark(bookMarkModel)
                                favorite.isSelected=true
                            }
                        }

                    }
                }


            }

            override fun isBookmark(item: Int?, favorite: ImageView) {
                CoroutineScope(Dispatchers.IO).launch {

                    val checkBookmark = item?.let { movieListViewModel.getBookmark(it) }
                    withContext(Dispatchers.Main){
                        favorite.isSelected = checkBookmark!=null

                    }
                }
            }
        })


        initRecyclerView()

        moviesListAdapter?.setContext(requireContext())

        binding.searchBar.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                getList(s.toString())
            }
        })
        getList("")

    }
    private fun getList(query:String){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                movieListViewModel.getMoviesListing(query).collectLatest {

                    moviesListAdapter?.submitData(it)
                }

            }
        }
    }


    private fun initRecyclerView() {

        val linearLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding. moviesRecyclerview.adapter = moviesListAdapter
        binding.moviesRecyclerview.layoutManager=linearLayoutManager

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding=null
    }
}
