package com.example.misandroid.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface MeasurementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeasurement(measurementEntity: MeasurementEntity)
}