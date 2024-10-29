package com.example.misandroid.ui.signal_map

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.misandroid.MainActivity
import com.example.misandroid.database.MeasurementEntity
import com.example.misandroid.database.UserSignalEntity
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SignalMapViewModel : ViewModel() {

    private val _measurementList = MainActivity.database.measurementDao.getMeasurements()

    private val _signalList = MainActivity.database.userSignalDao
        .getSignalsByUser(MainActivity.currentUser!!.macAddress)

    val signalList: LiveData<List<UserSignalEntity>> = _signalList.asLiveData()
    val measurementList: LiveData<List<MeasurementEntity>> = _measurementList.asLiveData()

    fun isMeasured(measurementId: Int?) : Boolean{
        if(measurementId == null)
            return false
        var isMeasured: Boolean = false
        runBlocking {
            val job = launch {
                isMeasured=MainActivity.database.userSignalDao.getSignalByUserAndId(
                    MainActivity.currentUser!!.macAddress,
                    measurementId
                ) != null
            }
            job.join()
        }
        return isMeasured

    }
}