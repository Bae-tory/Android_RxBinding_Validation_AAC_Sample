package com.project.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class RoomDataEntity {

    @Entity(tableName = "user")
    data class User(
        @PrimaryKey val id: Long?,
        val email: String?,
        val password: String?,
        val nickname: String?,
        val gender: String?,
        val birthday: String?,
        val agreeTerm: Boolean?,
        val agreeMarket: Boolean?
    )
}