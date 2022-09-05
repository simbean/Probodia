package com.example.probodia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probodia.data.remote.model.FoodDto
import com.example.probodia.repository.PreferenceRepository
import com.example.probodia.repository.ServerFoodRepository
import kotlinx.coroutines.launch

class SearchFoodViewModel : ViewModel() {

    private val serverFoodRepository = ServerFoodRepository()

    private val _result = MutableLiveData<Pair<Boolean, FoodDto>>()
    val result : LiveData<Pair<Boolean, FoodDto>>
        get() = _result

    fun getFood(isNewData : Boolean, preferenceRepository : PreferenceRepository, name : String, pageNo : Int) = viewModelScope.launch {
        val apiToken = preferenceRepository.getApiToken().apiAccessToken
        _result.value = Pair(isNewData, serverFoodRepository.getFoodList(apiToken, name, pageNo))
    }
}