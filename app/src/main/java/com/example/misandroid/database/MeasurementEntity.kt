package com.example.misandroid.database

import androidx.activity.result.contract.ActivityResultContracts
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measurements")
data class MeasurementEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val measurementId: Int,
    @ColumnInfo(name = "coordinates")
    val coordinates:String,
    @ColumnInfo(name = "strength")
    val strength:String,
    @ColumnInfo(name = "sensor")
    val sensor:String
)