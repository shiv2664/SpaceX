package com.shivam.spacex.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.shivam.spacex.fragments.listing.PagingSourceShips
import com.shivam.spacex.fragments.listing.model.BookMarkModel
import com.shivam.spacex.fragments.listing.model.XModel
import com.shivam.spacex.utility.network.ApiInterface
import com.shivam.spacex.utility.local.XDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SpaceXRepository @Inject constructor(private val apiInterface: ApiInterface, private val dao: XDao) {


    fun getShipsListing(query:String="") = Pager(
        config = PagingConfig(10, maxSize = 100, enablePlaceholders = true),
        pagingSourceFactory = { PagingSourceShips(searchKey = query, spaceXDao = dao) }).flow

    suspend fun getShipDetails(flightNumber:Int): XModel? = withContext(Dispatchers.IO) {
        return@withContext dao.getShipDetails(flightNumber)
    }


    suspend fun loadFromApi(url:String){
        val list =apiInterface.getShipList(url)
        list.body()?.let { dao.insertList(it) }

    }


    suspend fun getBookmark(flightNumber:Int): Int? = withContext(Dispatchers.IO) {
        return@withContext dao.getBookmark(flightNumber)
    }

    suspend fun deleteBookmark(flightNumber:Int){
        dao.deleteBookmark(flightNumber)
    }
    suspend fun insertBookmark(item:BookMarkModel){
        dao.insertBookmark(item)
    }



}