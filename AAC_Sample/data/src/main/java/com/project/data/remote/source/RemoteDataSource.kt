package com.project.data.remote.source

import com.project.data.component.Result
import com.project.data.model.TrackingInfo

interface RemoteDataSource {

    suspend fun getTrackingInfo(): Result<TrackingInfo>
}