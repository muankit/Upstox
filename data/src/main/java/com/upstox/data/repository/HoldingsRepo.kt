package com.upstox.data.repository

import com.upstox.data.models.UserHolding

interface HoldingsRepo {
    suspend fun getHoldings(fetchFromRemote: Boolean = true) : List<UserHolding>
}