package com.example.misandroid.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserSignalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserSignal(userSignalEntity: UserSignalEntity)
    @Query("SELECT * FROM user_signals WHERE user_mac=:userMac")
    fun getSignalByUser(userMac: String): Flow<List<UserSignalEntity>>
}