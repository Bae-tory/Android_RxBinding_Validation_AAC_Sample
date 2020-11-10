package com.project.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.data.local.model.RoomDataEntity

@Database(entities = [RoomDataEntity.TrackingInfo::class], version = 1, exportSchema = false)
@TypeConverters(RoomTypeConvertor::class)
abstract class SweetTrackerDataBase : RoomDatabase() {

    abstract fun contentDao(): SweetTrackerDao

    companion object {
        const val DB_NAME = "SweetTrackerDB"
    }

}