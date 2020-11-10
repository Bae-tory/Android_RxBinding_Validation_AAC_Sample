package com.project.data.di

import android.content.Context
import androidx.room.Room
import com.project.data.local.SweetTrackerDao
import com.project.data.local.SweetTrackerDataBase
import com.project.data.local.SweetTrackerDataBase.Companion.DB_NAME
import com.project.data.local.source.LocalDataSource
import com.project.data.local.source.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object LocalDataSourceModule {

    @Provides
    @Singleton
    fun provideContentDataBase(@ApplicationContext context: Context): SweetTrackerDataBase =
        Room.databaseBuilder(context, SweetTrackerDataBase::class.java, DB_NAME)
            .build()

    @Provides
    @Singleton
    fun provideContentDao(sweetTrackerDataBase: SweetTrackerDataBase): SweetTrackerDao =
        sweetTrackerDataBase.contentDao()

    @Provides
    @Singleton
    fun provideLocalDataSoruce(sweetTrackerDao: SweetTrackerDao): LocalDataSource =
        LocalDataSourceImpl(sweetTrackerDao)
}