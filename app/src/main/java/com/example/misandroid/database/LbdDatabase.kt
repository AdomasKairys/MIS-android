package com.example.misandroid.database

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [UserEntity::class, MeasurementEntity::class, UserSignalEntity::class],
    version = 1
)
abstract class LbdDatabase: RoomDatabase() {
    abstract val userDao: UserDao
    abstract val measurementDao: MeasurementDao
    abstract val userSignalDao: UserSignalDao
}