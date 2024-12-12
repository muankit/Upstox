package com.upstox.data.datasource

import com.upstox.data.models.UserHolding
import com.upstox.data.network.HoldingsApiService
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: HoldingsApiService
) : RemoteDataSource {
    override suspend fun getHoldings(): List<UserHolding> {
        return apiService.getHoldings().body()?.data?.userHolding?: listOf()
    }
}