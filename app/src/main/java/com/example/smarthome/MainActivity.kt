package com.example.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.smarthome.ui.dashboard.DashboardScreen
import com.example.smarthome.ui.dashboard.DashboardViewModel
import com.example.smarthome.ui.device.DeviceDetailScreen
import com.example.smarthome.ui.device.DeviceDetailViewModel
import com.example.smarthome.ui.floorplan.FloorPlanScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    val dashboardViewModel: DashboardViewModel = viewModel()

                    NavHost(navController = navController, startDestination = "dashboard") {
                        composable("dashboard") {
                            DashboardScreen(
                                viewModel = dashboardViewModel,
                                onFloorClick = { floorId ->
                                    navController.navigate("floorPlan/$floorId")
                                }
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
                        ) { _ ->
                            val deviceDetailViewModel: DeviceDetailViewModel = viewModel()
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
