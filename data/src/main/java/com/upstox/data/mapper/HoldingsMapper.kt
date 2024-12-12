package com.upstox.data.mapper

import com.upstox.data.models.UserHolding
import com.upstox.data.room.HoldingsEntity

fun List<UserHolding>.toHoldingsEntityList(): List<HoldingsEntity> {
    return this.map { userHolding ->
        HoldingsEntity(
            symbol = userHolding.symbol,
            quantity = userHolding.quantity,
            ltp = userHolding.ltp,
            avgPrice = userHolding.avgPrice,
            close = userHolding.close
        )
    }
}

fun List<HoldingsEntity>.toUserHoldingList(): List<UserHolding> {
    return this.map { holdingsEntity ->
        UserHolding(
            symbol = holdingsEntity.symbol,
            quantity = holdingsEntity.quantity,
            ltp = holdingsEntity.ltp,
            avgPrice = holdingsEntity.avgPrice,
            close = holdingsEntity.close
        )
    }
}