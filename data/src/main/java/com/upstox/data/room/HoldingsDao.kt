package com.upstox.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HoldingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHoldings(holdings: List<HoldingsEntity>)

    @Query("SELECT * FROM holdings_table")
    suspend fun getAllHoldings(): List<HoldingsEntity>
}