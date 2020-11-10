package com.project.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.data.local.model.RoomDataEntity

@Dao
interface SweetTrackerDao {

    @Query("SELECT * FROM trackingInfo")
    suspend fun getContentCache(): RoomDataEntity.TrackingInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackingInfo(trackingInfoLocal: RoomDataEntity.TrackingInfo)

}