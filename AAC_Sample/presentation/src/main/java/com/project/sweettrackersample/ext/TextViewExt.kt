package com.project.sweettrackersample.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.project.sweettrackersample.R

@BindingAdapter("bindDeliveryStatus")
fun TextView.bindDeliveryStatus(parcelLevel: Int? = null) {
    text =
        parcelLevel?.let {
            when (it) {
                0 -> context.getString(R.string.parcel_status_1)
                1 -> context.getString(R.string.parcel_status_2)
                2 -> context.getString(R.string.parcel_status_3)
                3 -> context.getString(R.string.parcel_status_4)
                else -> throw IllegalArgumentException(context.getString(R.string.error_delivery_status))
            }
        }
}