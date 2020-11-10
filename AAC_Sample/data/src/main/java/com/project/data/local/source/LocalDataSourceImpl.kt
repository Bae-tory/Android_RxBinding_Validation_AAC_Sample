package com.project.data.local.source

import com.project.data.component.Result
import com.project.data.local.SweetTrackerDao
import com.project.data.local.model.fromData
import com.project.data.local.model.toData
import com.project.data.model.TrackingInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class LocalDataSourceImpl @Inject constructor(private val sweetTrackerDao: SweetTrackerDao) :
    LocalDataSource {
    override suspend fun getTrackingInfo(): Result<TrackingInfo> =
        withContext(Dispatchers.IO) {
            try {
                Result.OnSuccess(sweetTrackerDao.getContentCache().toData())
            } catch (e: Exception) {
                Result.OnError(e)
            }
        }

    override suspend fun saveTrackingInfo(response: Result<TrackingInfo>): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                when (response) {
                    is Result.OnSuccess -> {
                        Result.OnSuccess(sweetTrackerDao.insertTrackingInfo(response.data.fromData()))
                    }
                    is Result.OnError ->
                        throw Exception("Error saveTrackingInfo")
                }
            } catch (e: Exception) {
                Result.OnError(e)
            }
        }
}