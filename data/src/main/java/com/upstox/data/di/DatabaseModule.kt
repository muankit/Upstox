package com.upstox.data.di

import android.content.Context
import androidx.room.Room
import com.upstox.data.room.HoldingsDao
import com.upstox.data.room.HoldingsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): HoldingsDatabase {
        return Room.databaseBuilder(
            context,
            HoldingsDatabase::class.java,
            "holdings_database"
        ).build()
    }

    @Provides
    fun provideUserHoldingDao(appDatabase: HoldingsDatabase): HoldingsDao {
        return appDatabase.holdingsDao()
    }
}