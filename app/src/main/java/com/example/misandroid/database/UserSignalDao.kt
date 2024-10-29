package com.example.misandroid.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserSignalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserSignal(userSignalEntity: UserSignalEntity)
    @Update
    fun updateUserSignal(userSignalEntity: UserSignalEntity)

    @Query("DELETE FROM user_signals WHERE id = :signalId")
    fun deleteSignalById(signalId: Int)
    @Query("SELECT * FROM user_signals WHERE user_mac=:userMac")
    fun getSignalsByUser(userMac: String): Flow<List<UserSignalEntity>>
    @Query("SELECT * FROM user_signals WHERE user_mac=:userMac AND measurement_id=:measurementId LIMIT 1")
    suspend fun getSignalByUserAndId(userMac: String, measurementId: Int): UserSignalEntity?
}