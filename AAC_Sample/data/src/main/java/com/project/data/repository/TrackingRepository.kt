package com.project.data.repository

import com.project.data.component.Result
import com.project.data.model.TrackingInfo

interface TrackingRepository {

    suspend fun requestRemoteTrackingInfo(): Result<TrackingInfo>
    suspend fun getCachedTrackingInfo(): Result<TrackingInfo>

}