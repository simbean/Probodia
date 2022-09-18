package com.example.probodia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probodia.data.remote.model.ApiMedicineDto
import com.example.probodia.repository.OpenApiMedicineRepository
import kotlinx.coroutines.launch

class SearchMedicineViewModel : BaseViewModel() {

    private val openApiMedicineRepository = OpenApiMedicineRepository()

    private val _medicineName = MutableLiveData<String>()
    val medicineName : LiveData<String>
        get() = _medicineName

    private val _result = MutableLiveData<Pair<Boolean, ApiMedicineDto>>()
    val result : LiveData<Pair<Boolean, ApiMedicineDto>>
        get() = _result

    fun getMedicine(reset : Boolean, name : String, pageNo : Int) = viewModelScope.launch(coroutineExceptionHandler) {
        _result.value = Pair(reset, openApiMedicineRepository.getMedicineList(name, pageNo))
    }

    fun setMedicineName(name : String) {
        _medicineName.value = name
    }
}