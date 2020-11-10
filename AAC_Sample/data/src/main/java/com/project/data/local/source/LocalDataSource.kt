package com.project.data.local.source

import com.project.data.component.Result
import com.project.data.model.TrackingInfo

interface LocalDataSource {

    suspend fun getTrackingInfo(): Result<TrackingInfo>
    suspend fun saveTrackingInfo(response: Result<TrackingInfo>): Result<Unit>
}