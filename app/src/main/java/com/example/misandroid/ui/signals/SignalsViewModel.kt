package com.example.misandroid.ui.signals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.misandroid.MainActivity
import com.example.misandroid.database.StrengthEntity

class SignalsViewModel : ViewModel() {

    private val _signalList = MainActivity.database.strengthDao.getStrength()

    val signalList: LiveData<List<StrengthEntity>> = _signalList.asLiveData()
}