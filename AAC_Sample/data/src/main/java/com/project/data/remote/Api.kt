package com.project.data.remote

import com.project.data.remote.model.TrackingInfoResponse
import retrofit2.http.GET

interface Api {
    @GET("")
    suspend fun getTrackingInfo(): TrackingInfoResponse
}