package com.project.rainist_android_test.ext

import android.view.View
import androidx.databinding.BindingAdapter
import com.project.rainist_android_test.component.ThrottleFirstClickListener

@BindingAdapter("onThrottleClick")
fun View.setOnThrottleClickListener(listener: View.OnClickListener) {
    setOnClickListener(
        ThrottleFirstClickListener { it.run(listener::onClick) }
    )
}
