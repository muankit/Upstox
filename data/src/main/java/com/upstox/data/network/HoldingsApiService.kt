package com.upstox.data.network

import com.upstox.data.models.HoldingsResponse
import retrofit2.Response
import retrofit2.http.GET

interface HoldingsApiService {
    @GET("/")
    suspend fun getHoldings(): Response<HoldingsResponse>
}