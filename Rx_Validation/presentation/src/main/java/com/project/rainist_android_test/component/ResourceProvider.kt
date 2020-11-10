package com.project.rainist_android_test.component

import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes res: Int): String

}