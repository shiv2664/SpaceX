package com.shivam.spacex.fragments.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivam.spacex.fragments.listing.model.XModel
import com.shivam.spacex.repository.SpaceXRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel@Inject constructor(private val repository: SpaceXRepository):ViewModel() {


    suspend fun getDetails(flightNumber:Int):XModel?= viewModelScope.async {
        return@async repository.getShipDetails(flightNumber)
    }.await()



}