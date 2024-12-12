package com.upstox.data.datasource

import com.upstox.data.models.UserHolding
import com.upstox.data.room.HoldingsEntity

interface LocalDataSource {
    suspend fun getHoldings(): List<UserHolding>
    suspend fun insertHoldings(holdings: List<HoldingsEntity>)
}