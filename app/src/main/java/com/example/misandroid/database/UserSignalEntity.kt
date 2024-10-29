package com.example.misandroid.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_signals",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = arrayOf("mac_address"),
            childColumns = arrayOf("user_mac")
        ),
        ForeignKey(
            entity = MeasurementEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("measurement_id")
        )
    ]
)
data class UserSignalEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "user_mac")
    val userMac: String,
    @ColumnInfo(name = "strength")
    val strength: String,
    @ColumnInfo(name = "measurement_id")
    val measurementId: Int
)
