package com.example.realestateapp.repository

import com.example.realestateapp.network.RetroServiceApi
import javax.inject.Inject

class HouseRepository @Inject constructor(private val api: RetroServiceApi) {

    suspend fun getHouseList()=api.getAllHouses()
}