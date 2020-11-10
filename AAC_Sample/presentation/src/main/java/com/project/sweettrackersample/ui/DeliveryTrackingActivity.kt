package com.project.sweettrackersample.ui

import androidx.activity.viewModels
import com.project.sweettrackersample.R
import com.project.sweettrackersample.base.BaseActivity
import com.project.sweettrackersample.databinding.ActivityDeliveryTrackingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeliveryTrackingActivity :
    BaseActivity<ActivityDeliveryTrackingBinding, DeliveryTrackingViewModel>(R.layout.activity_delivery_tracking) {
    override val vm: DeliveryTrackingViewModel by viewModels()
}
