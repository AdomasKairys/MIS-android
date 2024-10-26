package com.example.misandroid.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "strengths",
    foreignKeys = [
        ForeignKey(
            entity = MeasurementEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("measurement")
        )
    ]
)
data class StrengthEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val strengthId: Int,
    @ColumnInfo(name = "measurement")
    val measurementFk:Int,
    @ColumnInfo(name = "sensor")
    val sensor:String,
    @ColumnInfo(name = "strength")
    val strength:Int
)