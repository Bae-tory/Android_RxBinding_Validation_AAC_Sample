package com.project.sweettrackersample.model

data class TrackingInfoPersentation(
    val companyCode: String,
    val companyName: String,
    val invoice: String,
    val parcelLevel: Int,
    val deliverTime: String,
    val itemImg: String,
    val itemName: String,
    val itemDate: String
)

data class History(
    val trackingInfoPresentationList: List<HistoryItem>
)

data class HistoryItem(
    val time: String,
    val where: String,
    val status: String,
    val dDay: String? = "0"
)