package com.example.misandroid.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val userId: Int,
    @ColumnInfo(name = "mac_address")
    val macAddress:String,
    @ColumnInfo(name = "sensor")
    val sensor:String,
    @ColumnInfo(name = "strength")
    val strength:Int
)