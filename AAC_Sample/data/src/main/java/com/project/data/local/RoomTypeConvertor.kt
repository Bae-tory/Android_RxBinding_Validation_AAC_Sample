package com.project.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.data.model.TrackingInfoItem

class RoomTypeConvertor {

    private val gson = Gson()

    @TypeConverter
    fun toList(data: String?): List<TrackingInfoItem> = when (data) {
        null -> emptyList()
        else -> gson.fromJson<List<TrackingInfoItem>>(
            data,
            object : TypeToken<List<TrackingInfoItem>>() {}.type
        )
    }

    @TypeConverter
    fun toString(contents: List<TrackingInfoItem>): String {
        return gson.toJson(contents)
    }
}