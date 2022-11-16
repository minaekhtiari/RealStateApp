package com.example.realestateapp.network

import com.example.realestateapp.HousePojo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers



interface RetroServiceApi {

    @Headers("Access-Key:98bww4ezuzfePCYFxJEWyszbUXc7dxRx")
    @GET("house")
    suspend fun getAllHouses():Response<MutableList<HousePojo>>


}
