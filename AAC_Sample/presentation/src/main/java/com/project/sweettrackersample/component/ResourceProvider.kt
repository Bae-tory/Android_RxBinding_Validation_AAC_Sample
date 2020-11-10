package com.project.sweettrackersample.component

import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes res: Int): String

}