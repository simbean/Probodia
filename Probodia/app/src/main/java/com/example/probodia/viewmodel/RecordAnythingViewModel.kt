package com.example.probodia.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.probodia.repository.PreferenceRepository
import com.example.probodia.repository.ServerRepository
import java.time.LocalDateTime

class RecordAnythingViewModel(val num : Int) : ViewModel() {

    val _selectedTimeTag = MutableLiveData<Int>()
    val selectedTimeTag : LiveData<Int>
        get() = _selectedTimeTag

    val _localDateTime = MutableLiveData<LocalDateTime>(LocalDateTime.now())
    val localDateTime : LiveData<LocalDateTime>
            get() = _localDateTime

    val _buttonClickEnable = MutableLiveData(false)
    val buttonClickEnable : LiveData<Boolean>
        get() = _buttonClickEnable

    fun setSelectedTimeTag(kind : Int) {
        Log.e("SELECTED", kind.toString())
        _selectedTimeTag.value = kind
    }

    fun setButtonClickEnable(work : Boolean) {
        _buttonClickEnable.value = work
    }

    fun setLocalDateTime(localDateTime : LocalDateTime) {
        _localDateTime.value = localDateTime
    }
}