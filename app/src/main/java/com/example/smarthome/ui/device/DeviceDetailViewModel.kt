package com.example.smarthome.ui.device

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.model.Device
import com.example.smarthome.data.model.DeviceStatus
import com.example.smarthome.data.model.DeviceType
import com.example.smarthome.data.model.UsageLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val deviceId: String = savedStateHandle.get<String>("deviceId") ?: ""

    private val _device = MutableStateFlow<Device>(
        Device(
            id = deviceId,
            floorId = "floor_1",
            name = when (deviceId) {
                "dev_1" -> "Living Room Outlet"
                "dev_2" -> "Kitchen Light"
                "dev_3" -> "Master Hall Camera"
                else -> "Smart Device"
            },
            type = when (deviceId) {
                "dev_1" -> DeviceType.IRON_SLOT
                "dev_2" -> DeviceType.LIGHT_SCHEDULE
                "dev_3" -> DeviceType.CAMERA
                else -> DeviceType.OUTLET
            },
            status = DeviceStatus.ON,
            gridX = 1,
            gridY = 1,
            maxOnDurationMinutes = 30,
            turnedOnAt = System.currentTimeMillis() - 300000, // 5 minutes ago
            scheduleStart = "08:00",
            scheduleEnd = "22:00",
            switchCount = 2,
            switchStates = mapOf("Switch 1" to true, "Switch 2" to false),
            cameraSnapshotUrl = "https://picsum.photos/400/300"
        )
    )
    val device: StateFlow<Device> = _device.asStateFlow()

    fun toggleDeviceStatus(isOn: Boolean) {
        viewModelScope.launch {
            val current = _device.value
            val newStatus = if (isOn) DeviceStatus.ON else DeviceStatus.OFF
            val turnedOnTimestamp = if (isOn) System.currentTimeMillis() else null
            _device.value = current.copy(status = newStatus, turnedOnAt = turnedOnTimestamp)
            logUsage(if (isOn) "TURNED_ON" else "TURNED_OFF")
        }
    }

    fun toggleMultiSwitch(switchKey: String, isOn: Boolean) {
        viewModelScope.launch {
            val current = _device.value
            val currentMap = current.switchStates.orEmpty().toMutableMap()
            currentMap[switchKey] = isOn
            _device.value = current.copy(switchStates = currentMap)
            logUsage("SWITCH_${switchKey}_${if (isOn) "ON" else "OFF"}")
        }
    }

    fun updateMaxOnDuration(minutes: Int) {
        viewModelScope.launch {
            val current = _device.value
            _device.value = current.copy(maxOnDurationMinutes = minutes)
        }
    }

    fun updateSchedule(start: String, end: String) {
        viewModelScope.launch {
            val current = _device.value
            _device.value = current.copy(scheduleStart = start, scheduleEnd = end)
            logUsage("SCHEDULE_UPDATED_$start-$end")
        }
    }

    fun refreshCameraSnapshot() {
        viewModelScope.launch {
            val current = _device.value
            val refreshedUrl = "https://picsum.photos/400/300?random=${System.currentTimeMillis()}"
            _device.value = current.copy(cameraSnapshotUrl = refreshedUrl)
            logUsage("CAMERA_SNAPSHOT_REFRESHED")
        }
    }

    private fun logUsage(action: String) {
        val current = _device.value
        val log = UsageLog(
            id = java.util.UUID.randomUUID().toString(),
            deviceId = current.id,
            timestamp = System.currentTimeMillis(),
            action = action,
            durationMinutes = 5
        )
        // Mock repository log action simulation
    }
}
