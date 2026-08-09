package com.example.smarthome.ui.floorplan

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smarthome.data.model.Device
import com.example.smarthome.data.model.DeviceStatus
import com.example.smarthome.data.model.DeviceType
import com.example.smarthome.ui.dashboard.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloorPlanScreen(
    floorId: String,
    viewModel: DashboardViewModel,
    onBackClick: () -> Unit,
    onDeviceClick: (String) -> Unit
) {
    val floors by viewModel.floors.collectAsStateWithLifecycle()
    val devices by viewModel.devices.collectAsStateWithLifecycle()
    val floor = floors.find { it.id == floorId }
    val floorDevices = devices.filter { it.floorId == floorId }

    val rows = floor?.gridRows ?: 5
    val cols = floor?.gridCols ?: 5

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(floor?.name ?: "Floor Plan") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(0.9f)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                // Abstract grid lines
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val cellWidth = size.width / cols
                    val cellHeight = size.height / rows

                    for (i in 0..cols) {
                        drawLine(
                            color = Color.LightGray.copy(alpha = 0.5f),
                            start = androidx.compose.ui.geometry.Offset(i * cellWidth, 0f),
                            end = androidx.compose.ui.geometry.Offset(i * cellWidth, size.height),
                            strokeWidth = 2f
                        )
                    }
                    for (j in 0..rows) {
                        drawLine(
                            color = Color.LightGray.copy(alpha = 0.5f),
                            start = androidx.compose.ui.geometry.Offset(0f, j * cellHeight),
                            end = androidx.compose.ui.geometry.Offset(size.width, j * cellHeight),
                            strokeWidth = 2f
                        )
                    }
                }

                // Device icons positioned on grid
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val cellWidth = maxWidth / cols
                    val cellHeight = maxHeight / rows

                    floorDevices.forEach { device ->
                        val xOffset = cellWidth * device.gridX
                        val yOffset = cellHeight * device.gridY

                        val statusColor = when (device.status) {
                            DeviceStatus.ON -> Color(0xFF4CAF50) // Green
                            DeviceStatus.OFF -> Color(0xFF9E9E9E) // Grey
                            DeviceStatus.ERROR -> Color(0xFFF44336) // Red
                            DeviceStatus.DISCONNECTED -> Color(0xFFFF9800) // Amber
                        }

                        val iconVector = when (device.type) {
                            DeviceType.OUTLET -> Icons.Default.Power
                            DeviceType.MULTI_SWITCH -> Icons.Default.ToggleOn
                            DeviceType.IRON_SLOT -> Icons.Default.FlashOn
                            DeviceType.LIGHT_SCHEDULE -> Icons.Default.WbSunny
                            DeviceType.CAMERA -> Icons.Default.Videocam
                        }

                        Box(
                            modifier = Modifier
                                .offset(x = xOffset + (cellWidth - 48.dp) / 2, y = yOffset + (cellHeight - 48.dp) / 2)
                                .size(48.dp)
                                .background(statusColor.copy(alpha = 0.2f), shape = MaterialTheme.shapes.small)
                                .clickable { onDeviceClick(device.id) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = iconVector,
                                contentDescription = device.name,
                                tint = statusColor,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
