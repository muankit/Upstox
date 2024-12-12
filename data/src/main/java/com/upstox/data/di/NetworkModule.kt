package com.upstox.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.upstox.data.network.HoldingsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // Hardcoding the Base URL here for ease,
    // we can also take this from build config as well
    // and create dependency if used in multiple places.
    private const val BASE_URL = "https://35dee773a9ec441e9f38d5fc249406ce.api.mockbin.io"

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(
        gson: Gson,
    ): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        gsonFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonFactory)
            .build()
    }

    @Provides
    @Singleton
    fun providesUserApiService(retrofit: Retrofit): HoldingsApiService {
        return retrofit.create()
    }

}