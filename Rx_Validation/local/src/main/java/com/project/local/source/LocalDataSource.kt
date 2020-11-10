package com.project.local.source

import com.project.local.room.model.RoomDataEntity
import io.reactivex.Completable
import io.reactivex.Maybe

interface LocalDataSource {

    fun getUserByEmail(userEmail: String): Maybe<RoomDataEntity.User>

    fun insertUser(user: RoomDataEntity.User): Completable

}