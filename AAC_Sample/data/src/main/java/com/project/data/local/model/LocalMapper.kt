package com.project.data.local.model

import com.project.data.model.TrackingInfo
import com.project.data.model.TrackingInfoItem

fun RoomDataEntity.TrackingInfo.toData(): TrackingInfo =
    TrackingInfo(
        companyCode, companyName, invoice, parcelLevel, deliverTime, itemImg, itemName, itemDate,
        ArrayList<TrackingInfoItem>().also { arrayList ->
            this.trackingInfoList.forEach {
                arrayList.add(TrackingInfoItem(it.time, it.where, it.status))
            }
        }
    )

fun TrackingInfo.fromData(): RoomDataEntity.TrackingInfo =
    RoomDataEntity.TrackingInfo(
        System.currentTimeMillis(),
        companyCode,
        companyName,
        invoice,
        parcelLevel,
        deliverTime,
        itemImg,
        itemName,
        itemDate,
        ArrayList<TrackingInfoItem>().also { arrayList ->
            this.trackingInfoList.forEach {
                arrayList.add(TrackingInfoItem(it.time, it.where, it.status))
            }
        }
    )