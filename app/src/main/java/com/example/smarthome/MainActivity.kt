package com.example.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.runtime.getValue
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.smarthome.ui.dashboard.DashboardScreen
import com.example.smarthome.ui.dashboard.DashboardViewModel
import com.example.smarthome.ui.device.DeviceDetailScreen
import com.example.smarthome.ui.device.DeviceDetailViewModel
import com.example.smarthome.ui.floorplan.FloorPlanScreen
import com.example.smarthome.ui.reports.ReportsScreen
import com.example.smarthome.ui.reports.ReportsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    val dashboardViewModel: DashboardViewModel = hiltViewModel()
                    val reportsViewModel: ReportsViewModel = hiltViewModel()

                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    Scaffold(
                        bottomBar = {
                            if (currentRoute == "dashboard" || currentRoute == "reports") {
                                NavigationBar {
                                    NavigationBarItem(
                                        icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
                                        label = { Text("Dashboard") },
                                        selected = currentRoute == "dashboard",
                                        onClick = {
                                            if (currentRoute != "dashboard") {
                                                navController.navigate("dashboard") {
                                                    popUpTo("dashboard") { inclusive = false }
                                                }
                                            }
                                        }
                                    )
                                    NavigationBarItem(
                                        icon = { Icon(Icons.Default.Assessment, contentDescription = "Reports") },
                                        label = { Text("Reports") },
                                        selected = currentRoute == "reports",
                                        onClick = {
                                            if (currentRoute != "reports") {
                                                navController.navigate("reports") {
                                                    popUpTo("dashboard") { inclusive = false }
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "dashboard",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("dashboard") {
                                DashboardScreen(
                                    viewModel = dashboardViewModel,
                                    onFloorClick = { floorId ->
                                        navController.navigate("floorPlan/$floorId")
                                    }
                                )
                            }
                            composable("reports") {
                                ReportsScreen(
                                    viewModel = reportsViewModel
                                )
                            }
                            composable(
                                route = "floorPlan/{floorId}",
                                arguments = listOf(navArgument("floorId") { type = NavType.StringType })
                            ) { backStackEntry ->
                                val floorId = backStackEntry.arguments?.getString("floorId") ?: ""
                                FloorPlanScreen(
                                    floorId = floorId,
                                    viewModel = dashboardViewModel,
                                    onBackClick = { navController.popBackStack() },
                                    onDeviceClick = { deviceId ->
                                        navController.navigate("deviceDetail/$deviceId")
                                    }
                                )
                            }
                            composable(
                                route = "deviceDetail/{deviceId}",
                                arguments = listOf(navArgument("deviceId") { type = NavType.StringType })
                            ) { backStackEntry ->
                                val deviceDetailViewModel: DeviceDetailViewModel = hiltViewModel()
                                DeviceDetailScreen(
                                    viewModel = deviceDetailViewModel,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
