package com.project.data.repository

import com.project.data.component.ErrorType1Exception
import com.project.data.component.Result
import com.project.data.local.source.LocalDataSource
import com.project.data.model.TrackingInfo
import com.project.data.remote.source.RemoteDataSource

class TrackingRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : TrackingRepository {
    override suspend fun requestRemoteTrackingInfo(): Result<TrackingInfo> =
        when (val response = remoteDataSource.getTrackingInfo()) {
            is Result.OnSuccess -> {
                when (localDataSource.saveTrackingInfo(response)) {
                    is Result.OnSuccess -> getCachedTrackingInfo()
                    is Result.OnError -> throw Exception("Error localDataSource.saveTrackingInfo(response)")
                }
                Result.OnSuccess(response.data)
            }
            is Result.OnError -> throw Exception(ErrorType1Exception()/*sample*/)
        }

    override suspend fun getCachedTrackingInfo(): Result<TrackingInfo> =
        localDataSource.getTrackingInfo()

}