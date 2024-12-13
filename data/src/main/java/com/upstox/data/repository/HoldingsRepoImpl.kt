package com.upstox.data.repository

import com.upstox.data.datasource.LocalDataSource
import com.upstox.data.datasource.RemoteDataSource
import com.upstox.data.mapper.toHoldingsEntityList
import com.upstox.data.models.UserHolding
import javax.inject.Inject

class HoldingsRepoImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : HoldingsRepo {
    override suspend fun getHoldings(fetchFromRemote: Boolean): List<UserHolding> {
        return if (fetchFromRemote) {
            val holdings = remoteDataSource.getHoldings()

            val existingHoldings = localDataSource.getHoldings()
            val newHoldings = holdings.filter { holding ->
                existingHoldings.none { it.symbol == holding.symbol }
            }

            if (newHoldings.isNotEmpty()) {
                localDataSource.insertHoldings(newHoldings.toHoldingsEntityList())
            }
            holdings
        } else {
            localDataSource.getHoldings()
        }
    }
}