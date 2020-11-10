package com.project.sweettrackersample.component

import android.os.Parcelable
import androidx.annotation.StringRes
import com.project.sweettrackersample.R
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Delivery(@StringRes val id: Int) : Parcelable {
    InvoiceRegistration(R.string.parcel_status_0),
    PickUp(R.string.parcel_status_1),
    Departure(R.string.parcel_status_2),
    Shipping(R.string.parcel_status_3),
    Completed(R.string.parcel_status_4)
}