package com.upstox.data.datasource

import com.upstox.data.mapper.toUserHoldingList
import com.upstox.data.models.UserHolding
import com.upstox.data.room.HoldingsDao
import com.upstox.data.room.HoldingsEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val holdingsDao: HoldingsDao
): LocalDataSource {
    override suspend fun getHoldings(): List<UserHolding> {
        return holdingsDao.getAllHoldings().toUserHoldingList()
    }

    override suspend fun insertHoldings(holdings: List<HoldingsEntity>) {
        holdingsDao.insertHoldings(holdings)
    }
}