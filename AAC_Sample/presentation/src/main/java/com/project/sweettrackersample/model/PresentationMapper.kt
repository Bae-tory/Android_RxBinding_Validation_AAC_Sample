package com.project.sweettrackersample.model

import com.project.data.model.TrackingInfo

fun TrackingInfo.fromData(): Pair<TrackingInfoPersentation, History> =
    Pair(
        TrackingInfoPersentation(
            companyCode,
            companyName,
            invoice,
            parcelLevel,
            deliverTime,
            itemImg,
            itemName,
            itemDate
        ),
        History(ArrayList<HistoryItem>().also { arrayList ->
            this.trackingInfoList.forEach {
                arrayList.add(HistoryItem(it.time, it.where, it.status))
            }
        }
        )
    )
