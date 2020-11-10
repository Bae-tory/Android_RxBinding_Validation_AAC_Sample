package com.project.sweettrackersample.ext

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun Context.density(): Float {
    return resources.displayMetrics.density
}