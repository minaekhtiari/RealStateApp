package com.example.realestateapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.HousePojo
import com.example.realestateapp.repository.HouseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo:HouseRepository):ViewModel(){


    private val _house=MutableLiveData<MutableList<HousePojo>>()
    val house: LiveData<MutableList<HousePojo>>
    get() = _house

    init {
       getAllHouses()

    }

    private fun getAllHouses()=viewModelScope.launch {
       repo.getHouseList().let { response ->
           if (response.isSuccessful){
               _house.postValue(response.body())
               Log.e("ViewModel", "success")
           }else{
               Log.e("ViewModel", "error")
               TODO("Not yet implemented")
           }
       }

    }
}


