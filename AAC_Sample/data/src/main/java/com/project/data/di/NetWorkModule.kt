package com.project.data.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.project.data.BuildConfig
import com.project.data.component.Constants.CALL_TIME_OUT
import com.project.data.component.Constants.CONNECT_TIME_OUT
import com.project.data.component.Constants.READ_TIME_OUT
import com.project.data.component.Constants.WRITE_TIME_OUT
import com.project.data.component.Constants.cacheSize
import com.project.data.remote.SweetTrackerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetWorkModule {

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, cacheSize)

    @Provides
    @Singleton
    @Named("loggingInterceptor")
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    @Named("headerInterceptor")
    fun provideHeaderInterceptor(): Interceptor =
        Interceptor { chain ->
            val request =
                chain.request()
                    .newBuilder()
                    .build()

            chain.proceed(request)
        }

//    for RxJava
//    @Provides
//    @Singleton
//    @Named("CallAdapter")
//    fun provideCallAdapter(): CallAdapter.Factory =
//        RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    @Named("GsonConverter")
    fun provideConverter(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder()
            .setPrettyPrinting()
            .setVersion(1.0)
            .create()
        return GsonConverterFactory.create(gsonBuilder)
    }

    @Provides
    @Singleton
    @Named("OkHttpClient")
    fun provideOkHttpClient(
        cache: Cache,
        @Named("loggingInterceptor") loggingInterceptor: Interceptor,
        @Named("headerInterceptor") headerInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            .callTimeout(CALL_TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()


    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("GsonConverter") gsonConverter: GsonConverterFactory,
//        @Named("CallAdapter") callAdapter: CallAdapter.Factory,
        @Named("OkHttpClient") client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .client(client)
            .addConverterFactory(gsonConverter)
//            for RxJava
//            .addCallAdapterFactory(callAdapter)
            .baseUrl(BuildConfig.BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): SweetTrackerApi =
        retrofit.create(Api::class.java)


}
