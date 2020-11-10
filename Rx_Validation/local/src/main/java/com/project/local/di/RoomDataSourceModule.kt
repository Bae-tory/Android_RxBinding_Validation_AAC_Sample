package com.project.local.di

import android.content.Context
import androidx.room.Room
import com.project.local.room.TestRoomDataBase
import com.project.local.room.UserDao
import com.project.local.source.LocalDataSource
import com.project.local.source.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomDataSourceModule {

    const val DB_NAME = "TestDataBase"

    @Provides
    @Singleton
    fun provideContentDataBase(@ApplicationContext context: Context): TestRoomDataBase =
        Room.databaseBuilder(context, TestRoomDataBase::class.java, DB_NAME)
            .build()

    @Provides
    @Singleton
    fun provideContentDao(contentDataBase: TestRoomDataBase): UserDao =
        contentDataBase.userDao()

    @Provides
    @Singleton
    fun provideLocalDataSoruce(userDao: UserDao): LocalDataSource =
        LocalDataSourceImpl(userDao)
}