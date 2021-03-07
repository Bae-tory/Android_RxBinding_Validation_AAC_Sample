package com.project.baetory_rx_validation.model

import com.project.local.room.model.RoomDataEntity

data class User(
    val id: Long?,
    val email: String?,
    val password: String?,
    val nickname: String?,
    val gender: String?,
    val birthday: String?,
    val agreeTerm: Boolean?,
    val agreeMarket: Boolean?
)

fun User.toLocal(): RoomDataEntity.User =
    RoomDataEntity.User(id, email, password, nickname, gender, birthday, agreeTerm, agreeMarket)

fun RoomDataEntity.User.fromLocal(): User =
    User(id, email, password, nickname, gender, birthday, agreeTerm, agreeMarket)
