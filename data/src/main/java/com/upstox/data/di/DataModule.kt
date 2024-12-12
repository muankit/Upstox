package com.upstox.data.di

import com.upstox.data.datasource.LocalDataSource
import com.upstox.data.datasource.LocalDataSourceImpl
import com.upstox.data.datasource.RemoteDataSource
import com.upstox.data.datasource.RemoteDataSourceImpl
import com.upstox.data.repository.HoldingsRepo
import com.upstox.data.repository.HoldingsRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsHoldingsRepo(impl: HoldingsRepoImpl): HoldingsRepo

    @Binds
    fun bindsRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    fun bindsLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource
}