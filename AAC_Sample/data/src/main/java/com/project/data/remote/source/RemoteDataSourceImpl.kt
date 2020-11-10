package com.project.data.remote.source

import com.project.data.component.Result
import com.project.data.model.TrackingInfo
import com.project.data.remote.SweetTrackerApi
import com.project.data.remote.model.toData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class RemoteDataSourceImpl @Inject constructor(
    private val api: SweetTrackerApi
) : RemoteDataSource {
    override suspend fun getTrackingInfo(): Result<TrackingInfo> =
        withContext(Dispatchers.IO) {
            try {
                Result.OnSuccess(api.getTrackingInfo().toData())
            } catch (e: Exception) {
                Result.OnError(e)
            }
        }
}