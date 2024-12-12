package com.upstox.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HoldingsEntity::class], version = 1, exportSchema = true)
abstract class HoldingsDatabase : RoomDatabase() {
    abstract fun holdingsDao(): HoldingsDao
}