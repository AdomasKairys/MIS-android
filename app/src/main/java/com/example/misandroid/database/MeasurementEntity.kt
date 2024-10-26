package com.example.misandroid.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measurements")
data class MeasurementEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val measurementId: Int,
    @ColumnInfo(name = "x")
    val xCoordinate:Int,
    @ColumnInfo(name = "y")
    val yCoordinate:Int,
    @ColumnInfo(name = "distance")
    val distance:Double
)