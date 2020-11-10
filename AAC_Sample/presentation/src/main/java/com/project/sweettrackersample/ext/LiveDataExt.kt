package com.project.sweettrackersample.ext

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.createDefault(defaultValue: T): MutableLiveData<T> {
    value = defaultValue
    return this
}