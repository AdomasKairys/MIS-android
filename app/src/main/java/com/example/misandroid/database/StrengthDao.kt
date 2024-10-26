package com.example.misandroid.database

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StrengthDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStrength(strengthEntity: StrengthEntity)
    @Query("SELECT * FROM strengths")
    fun getStrength(): Flow<List<StrengthEntity>>
}