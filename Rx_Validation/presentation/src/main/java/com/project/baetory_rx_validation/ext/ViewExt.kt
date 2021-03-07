package com.project.baetory_rx_validation.ext

import android.view.View
import androidx.databinding.BindingAdapter
import com.project.baetory_rx_validation.component.ThrottleFirstClickListener

@BindingAdapter("onThrottleClick")
fun View.setOnThrottleClickListener(listener: View.OnClickListener) {
    setOnClickListener(
        ThrottleFirstClickListener { it.run(listener::onClick) }
    )
}
