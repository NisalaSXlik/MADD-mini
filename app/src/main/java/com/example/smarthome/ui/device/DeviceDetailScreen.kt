package com.example.smarthome.ui.device

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.smarthome.data.model.DeviceStatus
import com.example.smarthome.data.model.DeviceType
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceDetailScreen(
    viewModel: DeviceDetailViewModel,
    onBackClick: () -> Unit
) {
    val device by viewModel.device.collectAsStateWithLifecycle()

    // Live countdown ticker for Iron Slot / Safety Cutoff
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            currentTime = System.currentTimeMillis()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(device.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Status Card
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Device Type: ${device.type}", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Status: ${device.status}",
                            style = MaterialTheme.typography.titleMedium,
                            color = when (device.status) {
                                DeviceStatus.ON -> Color(0xFF4CAF50)
                                DeviceStatus.OFF -> Color(0xFF9E9E9E)
                                DeviceStatus.ERROR -> Color(0xFFF44336)
                                DeviceStatus.DISCONNECTED -> Color(0xFFFF9800)
                            }
                        )
                    }
                    Switch(
                        checked = device.status == DeviceStatus.ON,
                        onCheckedChange = { isOn -> viewModel.toggleDeviceStatus(isOn) }
                    )
                }
            }

            // Type-Specific Controls
            when (device.type) {
                DeviceType.OUTLET -> {
                    // Standard Outlet already handled via top switch card
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Box(modifier = Modifier.padding(16.dp)) {
                            Text("Standard Power Outlet control. Toggle above to turn power ON/OFF.")
                        }
                    }
                }
                DeviceType.MULTI_SWITCH -> {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Multi-Switch Channels", style = MaterialTheme.typography.titleMedium)
                            device.switchStates?.forEach { (switchKey, isOn) ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = switchKey)
                                    Switch(
                                        checked = isOn,
                                        onCheckedChange = { checked ->
                                            viewModel.toggleMultiSwitch(switchKey, checked)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                DeviceType.IRON_SLOT -> {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text("Iron Slot Safety Controls", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Max ON Duration: ${device.maxOnDurationMinutes ?: 30} minutes")
                            Slider(
                                value = (device.maxOnDurationMinutes ?: 30).toFloat(),
                                onValueChange = { viewModel.updateMaxOnDuration(it.toInt()) },
                                valueRange = 5f..120f,
                                steps = 22
                            )

                            if (device.status == DeviceStatus.ON && device.turnedOnAt != null && device.maxOnDurationMinutes != null) {
                                val elapsedMinutes = (currentTime - device.turnedOnAt!!) / 60000
                                val remainingMinutes = (device.maxOnDurationMinutes!! - elapsedMinutes).coerceAtLeast(0)
                                Text(
                                    text = "Safety Countdown: $remainingMinutes mins remaining before auto-cutoff",
                                    color = if (remainingMinutes <= 5) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
                DeviceType.LIGHT_SCHEDULE -> {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text("Lighting Automation Schedule", style = MaterialTheme.typography.titleMedium)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                OutlinedTextField(
                                    value = device.scheduleStart ?: "08:00",
                                    onValueChange = { start ->
                                        viewModel.updateSchedule(start, device.scheduleEnd ?: "22:00")
                                    },
                                    label = { Text("Start Time") },
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                OutlinedTextField(
                                    value = device.scheduleEnd ?: "22:00",
                                    onValueChange = { end ->
                                        viewModel.updateSchedule(device.scheduleStart ?: "08:00", end)
                                    },
                                    label = { Text("End Time") },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
                DeviceType.CAMERA -> {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Camera Feed / Snapshot", style = MaterialTheme.typography.titleMedium)
                                IconButton(onClick = { viewModel.refreshCameraSnapshot() }) {
                                    Icon(Icons.Default.Refresh, contentDescription = "Refresh Snapshot")
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = device.cameraSnapshotUrl,
                                    contentDescription = "Camera Snapshot",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
