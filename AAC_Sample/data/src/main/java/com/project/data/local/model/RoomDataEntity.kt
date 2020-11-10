package com.project.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.data.model.TrackingInfoItem

sealed class RoomDataEntity {

    @Entity(tableName = "trackingInfo")
    data class TrackingInfo(
        @PrimaryKey val id: Long,
        val companyCode: String,
        val companyName: String,
        val invoice: String,
        val parcelLevel: Int,
        val deliverTime: String,
        val itemImg: String,
        val itemName: String,
        val itemDate: String,
        val trackingInfoList: List<TrackingInfoItem>
    )
}
