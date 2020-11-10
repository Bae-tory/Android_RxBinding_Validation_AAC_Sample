package com.project.data.di

import com.project.data.local.source.LocalDataSource
import com.project.data.remote.source.RemoteDataSource
import com.project.data.repository.TrackingRepository
import com.project.data.repository.TrackingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTrackingRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): TrackingRepository = TrackingRepositoryImpl(localDataSource, remoteDataSource)

}