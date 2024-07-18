package com.shivam.spacex.fragments.listing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivam.spacex.fragments.listing.model.BookMarkModel
import com.shivam.spacex.fragments.listing.model.XModel
import com.shivam.spacex.repository.SpaceXRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShipsListingViewModel @Inject constructor(private val spaceXRepository: SpaceXRepository):ViewModel() {

    init {

        viewModelScope.launch {
            val url = "https://api.spacexdata.com/v3/launches"
            spaceXRepository.loadFromApi(url)

        }

    }

    fun getMoviesListing(query:String="") = spaceXRepository.getShipsListing(query)

    suspend fun getBookmark(flightNumber:Int): Int?= viewModelScope.async {
        return@async spaceXRepository.getBookmark(flightNumber)
    }.await()

    suspend fun deleteBookmark(flightNumber:Int){
        spaceXRepository.deleteBookmark(flightNumber)

    }

    suspend fun insertBookmark(item: BookMarkModel){
        spaceXRepository.insertBookmark(item)

    }

}