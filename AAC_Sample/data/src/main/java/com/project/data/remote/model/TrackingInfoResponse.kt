package com.project.data.remote.model

import com.google.gson.annotations.SerializedName

data class TrackingInfoResponse(
    @SerializedName("parcelCompanyCode")
    val companyCode: String,
    @SerializedName("parcelCompanyName")
    val companyName: String,
    @SerializedName("parcelInvoice")
    val invoice: String,
    val parcelLevel: Int,
    @SerializedName("parcelDeliverTime")
    val deliverTime: String,
    @SerializedName("purchaseItemImg")
    val itemImg: String,
    @SerializedName("purchaseItemName")
    val itemName: String,
    @SerializedName("purchaseItemDate")
    val itemDate: String,
    @SerializedName("trackingDetail")
    val trackingDetailList: List<TrackingDetailItem>
)

data class TrackingDetailItem(
    val time: String,
    val where: String,
    val status: String
)


