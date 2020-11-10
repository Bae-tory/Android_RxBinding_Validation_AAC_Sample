package com.project.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.local.room.model.RoomDataEntity
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE (email LIKE :userEmail) ORDER BY id DESC")
    fun getUserByEmail(userEmail: String): Maybe<RoomDataEntity.User>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertUser(user: RoomDataEntity.User): Completable

}