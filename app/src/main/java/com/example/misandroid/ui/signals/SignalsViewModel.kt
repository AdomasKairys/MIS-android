package com.example.misandroid.ui.signals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.misandroid.MainActivity
import com.example.misandroid.MainActivity.Companion.USER_KEY
import com.example.misandroid.MainActivity.Companion.sharedPreferences
import com.example.misandroid.database.StrengthEntity
import com.example.misandroid.database.UserSignalEntity

class SignalsViewModel : ViewModel() {

    private val _signalList = MainActivity.database.userSignalDao
        .getSignalByUser(sharedPreferences.getString(USER_KEY, "")!!)


    val signalList: LiveData<List<UserSignalEntity>> = _signalList.asLiveData()
}