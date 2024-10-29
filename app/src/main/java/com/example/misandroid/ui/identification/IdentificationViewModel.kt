package com.example.misandroid.ui.identification

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.misandroid.MainActivity
import com.example.misandroid.database.UserEntity

class IdentificationViewModel  : ViewModel() {

    private val _userList = MainActivity.database.userDao.getUsers()

    val usersList: LiveData<List<UserEntity>> = _userList.asLiveData()

    fun inserUser(userEntity: UserEntity){
        Thread{MainActivity.database.userDao.insertUser(userEntity)}.start()
    }

}