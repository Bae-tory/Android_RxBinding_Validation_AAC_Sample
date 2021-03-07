package com.project.baetory_rx_validation.di

import android.content.Context
import com.project.baetory_rx_validation.component.ResourceProvider
import com.project.baetory_rx_validation.component.ResourceProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ResourceProviderModule {

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider =
        ResourceProviderImpl(context)
}
