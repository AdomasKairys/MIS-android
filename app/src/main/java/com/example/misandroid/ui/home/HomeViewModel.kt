package com.example.misandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.misandroid.MainActivity.Companion.USER_KEY
import com.example.misandroid.MainActivity.Companion.sharedPreferences

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = sharedPreferences.getString(USER_KEY, "")
    }
    val text: LiveData<String> = _text
}