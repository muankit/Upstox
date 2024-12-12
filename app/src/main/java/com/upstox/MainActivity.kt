package com.upstox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.upstox.ui.navigation.BottomNavigationItem
import com.upstox.ui.navigation.Screens
import com.upstox.ui.screens.HoldingsScreen
import com.upstox.ui.screens.HoldingsViewModel
import com.upstox.ui.theme.UpstoxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            UpstoxTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(containerColor = Color.LightGray) {
                            BottomNavigationItem().bottomNavigationItems()
                                .forEachIndexed { _, navigationItem ->
                                    NavigationBarItem(
                                        selected = navigationItem.route == currentDestination?.route,
                                        label = {
                                            Text(navigationItem.label)
                                        },
                                        icon = {
                                            Icon(
                                                painter = painterResource(navigationItem.icon),
                                                contentDescription = navigationItem.label
                                            )
                                        },
                                        onClick = {
                                            navController.navigate(navigationItem.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    inclusive = true
                                                }
                                                launchSingleTop = true
                                            }
                                        },
                                        colors = NavigationBarItemColors(
                                            selectedTextColor = Color.Blue,
                                            selectedIndicatorColor = Color.Unspecified,
                                            unselectedIconColor = Color.DarkGray,
                                            unselectedTextColor = Color.DarkGray,
                                            disabledIconColor = Color.Transparent,
                                            disabledTextColor = Color.Unspecified,
                                            selectedIconColor = Color.Blue
                                        )
                                    )
                                }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Portfolio.route,
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    ) {

                        composable(Screens.Watchlist.route) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = Screens.Watchlist.route)
                            }
                        }

                        composable(Screens.Orders.route) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = Screens.Orders.route)
                            }
                        }

                        composable(Screens.Portfolio.route) {
                            val viewModel = hiltViewModel<HoldingsViewModel>()
                            val uiState by viewModel.holdingsUiState.collectAsStateWithLifecycle()

                            HoldingsScreen(
                                uiState = uiState
                            )
                        }

                        composable(Screens.Funds.route) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = Screens.Funds.route)
                            }
                        }

                        composable(Screens.Invest.route) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = Screens.Invest.route)
                            }
                        }
                    }
                }
            }
        }
    }
}
