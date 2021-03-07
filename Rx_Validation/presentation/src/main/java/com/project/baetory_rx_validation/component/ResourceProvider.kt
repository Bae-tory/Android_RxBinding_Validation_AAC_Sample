package com.project.baetory_rx_validation.component

import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes res: Int): String

}