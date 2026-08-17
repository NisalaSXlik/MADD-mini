package com.example.smarthome.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.model.Device
import com.example.smarthome.data.model.DeviceStatus
import com.example.smarthome.data.model.DeviceType
import com.example.smarthome.data.model.Floor
import com.example.smarthome.data.repository.SmartHomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

private const val MIN_GRID_SIZE = 2
private const val MAX_GRID_SIZE = 8

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: SmartHomeRepository
) : ViewModel() {

    val floors: StateFlow<List<Floor>> = repository.observeFloors()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val devices: StateFlow<List<Device>> = repository.observeDevices()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getDevicesForFloor(floorId: String): List<Device> {
        return devices.value.filter { it.floorId == floorId }
    }

    fun addFloor(name: String, gridRows: Int, gridCols: Int) {
        val newFloor = Floor(
            id = UUID.randomUUID().toString(),
            name = name,
            imageUrl = null,
            gridRows = gridRows.coerceIn(MIN_GRID_SIZE, MAX_GRID_SIZE),
            gridCols = gridCols.coerceIn(MIN_GRID_SIZE, MAX_GRID_SIZE)
        )
        repository.addFloor(newFloor)
    }

    fun updateFloor(floor: Floor, name: String, gridRows: Int, gridCols: Int) {
        val clampedRows = gridRows.coerceIn(MIN_GRID_SIZE, MAX_GRID_SIZE)
        val clampedCols = gridCols.coerceIn(MIN_GRID_SIZE, MAX_GRID_SIZE)
        val updatedFloor = floor.copy(name = name, gridRows = clampedRows, gridCols = clampedCols)
        val updates = devices.value
            .filter { it.floorId == floor.id && (it.gridX >= clampedCols || it.gridY >= clampedRows) }
            .map { it.copy(gridX = it.gridX.coerceIn(0, clampedCols - 1), gridY = it.gridY.coerceIn(0, clampedRows - 1)) }

        repository.updateFloor(updatedFloor)
        updates.forEach(repository::updateDevice)
    }

    fun deleteFloor(floorId: String) {
        repository.deleteFloor(floorId)
    }

    fun addDevice(
        floorId: String,
        name: String,
        type: DeviceType,
        gridX: Int,
        gridY: Int,
        switchCount: Int = 2,
        maxOnDurationMinutes: Int = 30,
        scheduleStart: String = "18:00",
        scheduleEnd: String = "23:00"
    ) {
        val normalizedSwitchCount = switchCount.coerceIn(1, 8)
        val newDevice = Device(
            id = UUID.randomUUID().toString(),
            floorId = floorId,
            name = name,
            type = type,
            status = DeviceStatus.OFF,
            gridX = gridX,
            gridY = gridY,
            maxOnDurationMinutes = if (type == DeviceType.IRON_SLOT) maxOnDurationMinutes else null,
            scheduleStart = if (type == DeviceType.LIGHT_SCHEDULE) scheduleStart else null,
            scheduleEnd = if (type == DeviceType.LIGHT_SCHEDULE) scheduleEnd else null,
            switchCount = if (type == DeviceType.MULTI_SWITCH) normalizedSwitchCount else null,
            switchStates = if (type == DeviceType.MULTI_SWITCH) {
                (1..normalizedSwitchCount).associate { "Switch $it" to false }
            } else {
                null
            },
            cameraSnapshotUrl = if (type == DeviceType.CAMERA) {
                "https://picsum.photos/400/300?random=${System.currentTimeMillis()}"
            } else {
                null
            }
        )
        repository.addDevice(newDevice)
    }

    fun updateDevice(device: Device) {
        repository.updateDevice(device)
    }

    fun moveDevice(device: Device, gridX: Int, gridY: Int) {
        repository.updateDevice(device.copy(gridX = gridX, gridY = gridY))
    }

    fun deleteDevice(deviceId: String) {
        repository.deleteDevice(deviceId)
    }
}
