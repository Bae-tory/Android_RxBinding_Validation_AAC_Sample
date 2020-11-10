package com.project.local.source

import com.project.local.room.model.RoomDataEntity
import com.project.local.room.UserDao
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val userDao: UserDao) : LocalDataSource {

    override fun getUserByEmail(userEmail: String): Maybe<RoomDataEntity.User> =
        userDao.getUserByEmail(userEmail)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    override fun insertUser(user: RoomDataEntity.User): Completable =
        userDao.insertUser(user)
            .subscribeOn(Schedulers.io())

}