package com.project.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.local.room.model.RoomDataEntity

@Database(entities = [RoomDataEntity.User::class], version = 1, exportSchema = false)
abstract class TestRoomDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

}