package com.upstox.ui.navigation

import com.upstox.R

data class BottomNavigationItem(
    val label : String = "",
    val icon : Int = R.drawable.ic_watchlist,
    val route : String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Watchlist",
                icon = R.drawable.ic_watchlist,
                route = Screens.Watchlist.route
            ),
            BottomNavigationItem(
                label = "Orders",
                icon = R.drawable.ic_orders,
                route = Screens.Orders.route
            ),
            BottomNavigationItem(
                label = "Portfolio",
                icon = R.drawable.ic_portfolio,
                route = Screens.Portfolio.route
            ),
            BottomNavigationItem(
                label = "Funds",
                icon = R.drawable.ic_funds,
                route = Screens.Funds.route
            ),
            BottomNavigationItem(
                label = "Invest",
                icon = R.drawable.ic_invest,
                route = Screens.Invest.route
            ),
        )
    }
}