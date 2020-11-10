package com.project.data.model

data class TrackingInfo(
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

data class TrackingInfoItem(
    val time: String,
    val where: String,
    val status: String
)

