package com.upstox.data.models

import androidx.annotation.Keep

@Keep
data class HoldingsResponse(
    val data: Data? = Data()
)

@Keep
data class Data(
    val userHolding: List<UserHolding> = listOf()
)

@Keep
data class UserHolding(
    val symbol: String? = null,
    val quantity: Int? = null,
    val ltp: Double? = null,
    val avgPrice: Double? = null,
    val close: Double? = null,
) {
    private val currentValue: Double
        get() = (ltp ?: 0.0) * (quantity ?: 0)

    private val totalInvestment: Double
        get() = (avgPrice ?: 0.0) * (quantity ?: 0)

    val totalPNL: Double
        get() = currentValue - totalInvestment
}