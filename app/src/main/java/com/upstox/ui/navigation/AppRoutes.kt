package com.upstox.ui.navigation

sealed class Screens(val route : String) {
    object Watchlist : Screens("watchlist_route")
    object Orders : Screens("orders_route")
    object Portfolio : Screens("portfolio_route")
    object Funds : Screens("funds_route")
    object Invest : Screens("invest_route")
}