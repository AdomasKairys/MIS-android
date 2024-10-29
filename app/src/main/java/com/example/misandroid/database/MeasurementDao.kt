package com.example.misandroid.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasurementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeasurement(measurementEntity: MeasurementEntity)

    @Query("SELECT * FROM measurements")
    fun getMeasurements(): Flow<List<MeasurementEntity>>

    @Query("SELECT * FROM measurements WHERE id=:measurementId")
    fun getMeasurementsById(measurementId:Int): MeasurementEntity?
}