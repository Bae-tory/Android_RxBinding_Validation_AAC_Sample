package com.project.data.di

import com.project.data.remote.SweetTrackerApi
import com.project.data.remote.source.RemoteDataSource
import com.project.data.remote.source.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSoruce(api: SweetTrackerApi): RemoteDataSource =
        RemoteDataSourceImpl(api)
}
