package com.upstox.data.datasource

import com.upstox.data.models.UserHolding

interface RemoteDataSource {
    suspend fun getHoldings(): List<UserHolding>
}