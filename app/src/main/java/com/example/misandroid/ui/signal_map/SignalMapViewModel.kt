package com.example.misandroid.ui.signal_map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.misandroid.MainActivity
import com.example.misandroid.MainActivity.Companion.sharedPreferences
import com.example.misandroid.database.MeasurementEntity

class SignalMapViewModel : ViewModel() {

    private val _measurementList = MainActivity.database.measurementDao.getMeasurements()

    val measurementList: LiveData<List<MeasurementEntity>> = _measurementList.asLiveData()
}