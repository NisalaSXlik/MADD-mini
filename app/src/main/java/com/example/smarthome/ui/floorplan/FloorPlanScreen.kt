package com.example.smarthome.ui.floorplan

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
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

    val rows = (floor?.gridRows ?: 5).coerceIn(2, 8)
    val cols = (floor?.gridCols ?: 5).coerceIn(2, 8)
    val visibleFloorDevices = floorDevices.map {
        it.copy(
            gridX = it.gridX.coerceIn(0, cols - 1),
            gridY = it.gridY.coerceIn(0, rows - 1)
        )
    }

    var selectedCell by remember(floorId) { mutableStateOf<Pair<Int, Int>?>(null) }
    var selectedDevice by remember(floorId) { mutableStateOf<Device?>(null) }
    var movingDevice by remember(floorId) { mutableStateOf<Device?>(null) }
    var editingDevice by remember(floorId) { mutableStateOf<Device?>(null) }
    var deletingDevice by remember(floorId) { mutableStateOf<Device?>(null) }

    LaunchedEffect(devices, selectedDevice?.id, editingDevice?.id, deletingDevice?.id, movingDevice?.id) {
        selectedDevice = selectedDevice?.let { current -> devices.find { it.id == current.id } }
        editingDevice = editingDevice?.let { current ->
            if (current.id.isBlank()) current else devices.find { it.id == current.id }
        }
        deletingDevice = deletingDevice?.let { current -> devices.find { it.id == current.id } }
        movingDevice = movingDevice?.let { current -> devices.find { it.id == current.id } }
    }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = movingDevice?.let { "Tap a grid cell to move ${it.name}" }
                    ?: "Tap an empty grid cell to add a device. Tap a device to edit, move, delete, or open controls.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    DeviceGrid(
                        rows = rows,
                        cols = cols,
                        devices = visibleFloorDevices,
                        selectedDeviceId = selectedDevice?.id,
                        movingDeviceId = movingDevice?.id,
                        onCellClick = { x, y ->
                            val deviceAtCell = visibleFloorDevices.firstOrNull { it.gridX == x && it.gridY == y }
                            val deviceToMove = movingDevice
                            when {
                                deviceToMove != null -> {
                                    viewModel.moveDevice(deviceToMove, x, y)
                                    movingDevice = null
                                    selectedDevice = deviceToMove.copy(gridX = x, gridY = y)
                                    selectedCell = null
                                }
                                deviceAtCell != null -> {
                                    selectedDevice = deviceAtCell
                                    selectedCell = null
                                }
                                else -> {
                                    selectedCell = x to y
                                    selectedDevice = null
                                }
                            }
                        },
                        onDeviceClick = { device ->
                            val deviceToMove = movingDevice
                            if (deviceToMove != null && deviceToMove.id != device.id) {
                                viewModel.moveDevice(deviceToMove, device.gridX, device.gridY)
                                movingDevice = null
                                selectedDevice = deviceToMove.copy(gridX = device.gridX, gridY = device.gridY)
                                selectedCell = null
                            } else {
                                selectedDevice = device
                                selectedCell = null
                            }
                        }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(156.dp)
            ) {
                selectedDevice?.let { device ->
                    DeviceActionPanel(
                        device = device,
                        isMoving = movingDevice?.id == device.id,
                        onOpen = { onDeviceClick(device.id) },
                        onEdit = { editingDevice = device },
                        onMove = { movingDevice = device },
                        onCancelMove = { movingDevice = null },
                        onDelete = { deletingDevice = device }
                    )
                } ?: selectedCell?.let { (x, y) ->
                    Button(
                        onClick = { editingDevice = Device(floorId = floorId, gridX = x, gridY = y) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add device at row ${y + 1}, column ${x + 1}")
                    }
                }
            }
        }

        editingDevice?.let { device ->
            DeviceEditorDialog(
                device = device,
                isNew = device.id.isBlank(),
                onDismiss = { editingDevice = null },
                onSave = { updated ->
                    if (updated.id.isBlank()) {
                        viewModel.addDevice(
                            floorId = floorId,
                            name = updated.name,
                            type = updated.type,
                            gridX = updated.gridX,
                            gridY = updated.gridY,
                            switchCount = updated.switchCount ?: 2,
                            maxOnDurationMinutes = updated.maxOnDurationMinutes ?: 30,
                            scheduleStart = updated.scheduleStart ?: "18:00",
                            scheduleEnd = updated.scheduleEnd ?: "23:00"
                        )
                    } else {
                        viewModel.updateDevice(updated)
                    }
                    editingDevice = null
                    selectedCell = null
                }
            )
        }

        deletingDevice?.let { device ->
            AlertDialog(
                onDismissRequest = { deletingDevice = null },
                title = { Text("Delete Device") },
                text = { Text("Delete ${device.name} from this floor plan?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteDevice(device.id)
                            deletingDevice = null
                            selectedDevice = null
                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { deletingDevice = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
private fun DeviceGrid(
    rows: Int,
    cols: Int,
    devices: List<Device>,
    selectedDeviceId: String?,
    movingDeviceId: String?,
    onCellClick: (Int, Int) -> Unit,
    onDeviceClick: (Device) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cellWidth = size.width / cols
            val cellHeight = size.height / rows

            for (i in 0..cols) {
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.55f),
                    start = Offset(i * cellWidth, 0f),
                    end = Offset(i * cellWidth, size.height),
                    strokeWidth = 2f
                )
            }
            for (j in 0..rows) {
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.55f),
                    start = Offset(0f, j * cellHeight),
                    end = Offset(size.width, j * cellHeight),
                    strokeWidth = 2f
                )
            }

            val selectedDevice = devices.firstOrNull { it.id == selectedDeviceId }
            val movingDevice = devices.firstOrNull { it.id == movingDeviceId }
            listOfNotNull(selectedDevice, movingDevice).distinctBy { it.id }.forEach { device ->
                val x = device.gridX * cellWidth
                val y = device.gridY * cellHeight
                val isMoving = device.id == movingDeviceId
                drawRect(
                    color = if (isMoving) Color(0xFF1565C0) else Color(0xFF2E7D32),
                    topLeft = Offset(x + 5f, y + 5f),
                    size = Size(cellWidth - 10f, cellHeight - 10f),
                    style = Stroke(
                        width = if (isMoving) 5f else 4f,
                        pathEffect = if (isMoving) PathEffect.dashPathEffect(floatArrayOf(18f, 12f)) else null
                    )
                )
            }
        }

        val cellWidth = maxWidth / cols
        val cellHeight = maxHeight / rows

        repeat(rows) { y ->
            repeat(cols) { x ->
                Box(
                    modifier = Modifier
                        .offset(x = cellWidth * x, y = cellHeight * y)
                        .width(cellWidth)
                        .height(cellHeight)
                        .clickable { onCellClick(x, y) }
                )
            }
        }

        devices.forEach { device ->
            val statusColor = device.status.statusColor()
            val isSelected = device.id == selectedDeviceId
            val isMoving = device.id == movingDeviceId
            Box(
                modifier = Modifier
                    .offset(
                        x = cellWidth * device.gridX + (cellWidth - 44.dp) / 2,
                        y = cellHeight * device.gridY + (cellHeight - 44.dp) / 2
                    )
                    .size(44.dp)
                    .background(statusColor.copy(alpha = 0.18f), shape = CircleShape)
                    .border(
                        width = if (isSelected || isMoving) 3.dp else 1.dp,
                        color = if (isMoving) Color(0xFF1565C0) else if (isSelected) Color(0xFF2E7D32) else statusColor.copy(alpha = 0.65f),
                        shape = CircleShape
                    )
                    .clickable { onDeviceClick(device) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = device.type.icon(),
                    contentDescription = device.name,
                    tint = statusColor,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}

@Composable
private fun DeviceActionPanel(
    device: Device,
    isMoving: Boolean,
    onOpen: () -> Unit,
    onEdit: () -> Unit,
    onMove: () -> Unit,
    onCancelMove: () -> Unit,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(device.name, style = MaterialTheme.typography.titleMedium)
                    Text(
                        if (isMoving) {
                            "Move mode active - tap a destination cell"
                        } else {
                            "${device.type.displayName()} - ${device.status}"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isMoving) Color(0xFF1565C0) else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit device")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete device")
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onOpen, modifier = Modifier.weight(1f)) {
                    Text("Controls")
                }
                Button(
                    onClick = if (isMoving) onCancelMove else onMove,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (isMoving) "Cancel Move" else "Move")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeviceEditorDialog(
    device: Device,
    isNew: Boolean,
    onDismiss: () -> Unit,
    onSave: (Device) -> Unit
) {
    var name by remember(device.id, device.gridX, device.gridY, isNew) { mutableStateOf(if (isNew) "" else device.name) }
    var type by remember(device.id, device.gridX, device.gridY, isNew) { mutableStateOf(device.type) }
    var switchCount by remember(device.id, device.gridX, device.gridY, isNew) { mutableStateOf((device.switchCount ?: 2).toString()) }
    var maxDuration by remember(device.id, device.gridX, device.gridY, isNew) { mutableStateOf((device.maxOnDurationMinutes ?: 30).toString()) }
    var scheduleStart by remember(device.id, device.gridX, device.gridY, isNew) { mutableStateOf(device.scheduleStart ?: "18:00") }
    var scheduleEnd by remember(device.id, device.gridX, device.gridY, isNew) { mutableStateOf(device.scheduleEnd ?: "23:00") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isNew) "Add Device" else "Edit Device") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Device Name") },
                    singleLine = true
                )

                Text("Device Type", style = MaterialTheme.typography.labelLarge)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DeviceTypeChip(DeviceType.OUTLET, type) { type = it }
                        DeviceTypeChip(DeviceType.MULTI_SWITCH, type) { type = it }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DeviceTypeChip(DeviceType.IRON_SLOT, type) { type = it }
                        DeviceTypeChip(DeviceType.LIGHT_SCHEDULE, type) { type = it }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DeviceTypeChip(DeviceType.CAMERA, type) { type = it }
                    }
                }

                when (type) {
                    DeviceType.MULTI_SWITCH -> OutlinedTextField(
                        value = switchCount,
                        onValueChange = { switchCount = it.filter(Char::isDigit).take(1) },
                        label = { Text("Switch Count") },
                        singleLine = true
                    )
                    DeviceType.IRON_SLOT -> OutlinedTextField(
                        value = maxDuration,
                        onValueChange = { maxDuration = it.filter(Char::isDigit).take(3) },
                        label = { Text("Max ON Minutes") },
                        singleLine = true
                    )
                    DeviceType.LIGHT_SCHEDULE -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = scheduleStart,
                            onValueChange = { scheduleStart = it.take(5) },
                            label = { Text("Start") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = scheduleEnd,
                            onValueChange = { scheduleEnd = it.take(5) },
                            label = { Text("End") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    else -> Spacer(modifier = Modifier.height(0.dp))
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(selected = true, onClick = {}, label = { Text("Row ${device.gridY + 1}") })
                    FilterChip(selected = true, onClick = {}, label = { Text("Column ${device.gridX + 1}") })
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank()) {
                        val count = switchCount.toIntOrNull()?.coerceIn(1, 8) ?: 2
                        val updatedSwitchStates = if (type == DeviceType.MULTI_SWITCH) {
                            val existing = device.switchStates.orEmpty()
                            (1..count).associate { index -> "Switch $index" to (existing["Switch $index"] ?: false) }
                        } else {
                            null
                        }
                        onSave(
                            device.copy(
                                name = name.trim(),
                                type = type,
                                maxOnDurationMinutes = if (type == DeviceType.IRON_SLOT) {
                                    maxDuration.toIntOrNull()?.coerceIn(1, 240) ?: 30
                                } else {
                                    null
                                },
                                scheduleStart = if (type == DeviceType.LIGHT_SCHEDULE) scheduleStart else null,
                                scheduleEnd = if (type == DeviceType.LIGHT_SCHEDULE) scheduleEnd else null,
                                switchCount = if (type == DeviceType.MULTI_SWITCH) count else null,
                                switchStates = updatedSwitchStates,
                                cameraSnapshotUrl = if (type == DeviceType.CAMERA) {
                                    device.cameraSnapshotUrl ?: "https://picsum.photos/400/300?random=${System.currentTimeMillis()}"
                                } else {
                                    null
                                }
                            )
                        )
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun RowScope.DeviceTypeChip(
    option: DeviceType,
    selectedType: DeviceType,
    onSelect: (DeviceType) -> Unit
) {
    FilterChip(
        selected = selectedType == option,
        onClick = { onSelect(option) },
        label = { Text(option.displayName()) },
        modifier = Modifier.weight(1f)
    )
}

private fun DeviceStatus.statusColor(): Color = when (this) {
    DeviceStatus.ON -> Color(0xFF2E7D32)
    DeviceStatus.OFF -> Color(0xFF616161)
    DeviceStatus.ERROR -> Color(0xFFC62828)
    DeviceStatus.DISCONNECTED -> Color(0xFFF57C00)
}

private fun DeviceType.icon(): ImageVector = when (this) {
    DeviceType.OUTLET -> Icons.Default.Power
    DeviceType.MULTI_SWITCH -> Icons.Default.ToggleOn
    DeviceType.IRON_SLOT -> Icons.Default.FlashOn
    DeviceType.LIGHT_SCHEDULE -> Icons.Default.WbSunny
    DeviceType.CAMERA -> Icons.Default.Videocam
}

private fun DeviceType.displayName(): String = when (this) {
    DeviceType.OUTLET -> "Outlet"
    DeviceType.MULTI_SWITCH -> "Multi Switch"
    DeviceType.IRON_SLOT -> "Iron Slot"
    DeviceType.LIGHT_SCHEDULE -> "Scheduled Light"
    DeviceType.CAMERA -> "Camera"
}
