package com.example.misandroid.ui.signals

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.misandroid.MainActivity
import com.example.misandroid.MainActivity.Companion.currentUser
import com.example.misandroid.database.MeasurementEntity
import com.example.misandroid.database.UserSignalEntity

class SignalsViewModel : ViewModel() {

    private val _signalList = MainActivity.database.userSignalDao
        .getSignalsByUser(currentUser!!.macAddress)

    private val _measurementList = MainActivity.database.measurementDao
        .getMeasurements()

    val signalList: LiveData<List<UserSignalEntity>> = _signalList.asLiveData()
    val measurementList: LiveData<List<MeasurementEntity>> = _measurementList.asLiveData()

    fun insertSignal(userSignalEntity: UserSignalEntity){
        Thread{MainActivity.database.userSignalDao.insertUserSignal(userSignalEntity)}.start()
    }
    fun editSignal(userSignalEntity: UserSignalEntity){
        Thread{MainActivity.database.userSignalDao.updateUserSignal(userSignalEntity)}.start()
    }
    fun deleteSignal(signalId: Int){
        Thread{MainActivity.database.userSignalDao.deleteSignalById(signalId)}.start()
    }
}