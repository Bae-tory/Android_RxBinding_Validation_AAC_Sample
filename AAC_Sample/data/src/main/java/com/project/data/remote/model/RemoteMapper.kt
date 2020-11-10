package com.project.data.remote.model

import com.project.data.model.TrackingInfo
import com.project.data.model.TrackingInfoItem


fun TrackingInfoResponse.toData(): TrackingInfo =
    TrackingInfo(
        companyCode, companyName, invoice, parcelLevel, deliverTime, itemImg, itemName, itemDate,
        ArrayList<TrackingInfoItem>().also { arrayList ->
            this.trackingDetailList.forEach {
                arrayList.add(TrackingInfoItem(it.time, it.where, it.status))
            }
        }
    )