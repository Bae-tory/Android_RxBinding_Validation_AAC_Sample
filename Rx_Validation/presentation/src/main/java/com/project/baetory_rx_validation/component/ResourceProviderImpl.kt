package com.project.baetory_rx_validation.component

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext

class ResourceProviderImpl(@ApplicationContext private val context: Context) : ResourceProvider {

    override fun getString(res: Int) = context.getString(res)
}