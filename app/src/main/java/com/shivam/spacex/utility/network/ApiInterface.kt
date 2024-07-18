package com.shivam.spacex.utility.network

import com.shivam.spacex.fragments.listing.model.XModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {

    @GET
    suspend fun getShipList(@Url url:String):Response<List<XModel>>

}