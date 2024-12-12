package com.upstox.data.room

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "holdings_table")
data class HoldingsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val symbol: String? = null,
    val quantity: Int? = null,
    val ltp: Double? = null,
    val avgPrice: Double? = null,
    val close: Double? = null
) {
    @get:Ignore
    private val currentValue: Double
        get() = (ltp ?: 0.0) * (quantity ?: 0)

    @get:Ignore
    private val totalInvestment: Double
        get() = (avgPrice ?: 0.0) * (quantity ?: 0)

    @get:Ignore
    val totalPNL: Double
        get() = currentValue - totalInvestment

}
