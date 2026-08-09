package com.example.smarthome.ui.device

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.model.Device
import com.example.smarthome.data.model.DeviceStatus
import com.example.smarthome.data.model.UsageLog
import com.example.smarthome.data.repository.SmartHomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SmartHomeRepository
) : ViewModel() {

    private val deviceId: String = savedStateHandle.get<String>("deviceId") ?: ""

    val device: StateFlow<Device> = repository.observeDevices()
        .map { list -> list.find { it.id == deviceId } ?: Device(id = deviceId, name = "Unknown Device") }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            Device(id = deviceId, name = "Loading...")
        )

    fun toggleDeviceStatus(isOn: Boolean) {
        viewModelScope.launch {
            val current = device.value
            val newStatus = if (isOn) DeviceStatus.ON else DeviceStatus.OFF
            val turnedOnTimestamp = if (isOn) System.currentTimeMillis() else null
            val updated = current.copy(status = newStatus, turnedOnAt = turnedOnTimestamp)
            repository.updateDevice(updated)
            logUsage(if (isOn) "TURNED_ON" else "TURNED_OFF")
        }
    }

    fun setDeviceStatus(newStatus: DeviceStatus) {
        viewModelScope.launch {
            val current = device.value
            val turnedOnTimestamp = if (newStatus == DeviceStatus.ON) System.currentTimeMillis() else current.turnedOnAt
            val updated = current.copy(status = newStatus, turnedOnAt = turnedOnTimestamp)
            repository.updateDevice(updated)
            logUsage("STATUS_SET_${newStatus.name}")
        }
    }

    fun toggleMultiSwitch(switchKey: String, isOn: Boolean) {
        viewModelScope.launch {
            val current = device.value
            val currentMap = current.switchStates.orEmpty().toMutableMap()
            currentMap[switchKey] = isOn
            val updated = current.copy(switchStates = currentMap)
            repository.updateDevice(updated)
            logUsage("SWITCH_${switchKey}_${if (isOn) "ON" else "OFF"}")
        }
    }

    fun updateMaxOnDuration(minutes: Int) {
        viewModelScope.launch {
            val current = device.value
            val updated = current.copy(maxOnDurationMinutes = minutes)
            repository.updateDevice(updated)
        }
    }

    fun updateSchedule(start: String, end: String) {
        viewModelScope.launch {
            val current = device.value
            val updated = current.copy(scheduleStart = start, scheduleEnd = end)
            repository.updateDevice(updated)
            logUsage("SCHEDULE_UPDATED_$start-$end")
        }
    }

    fun refreshCameraSnapshot() {
        viewModelScope.launch {
            val current = device.value
            val refreshedUrl = "https://picsum.photos/400/300?random=${System.currentTimeMillis()}"
            val updated = current.copy(cameraSnapshotUrl = refreshedUrl)
            repository.updateDevice(updated)
            logUsage("CAMERA_SNAPSHOT_REFRESHED")
        }
    }

    private fun logUsage(action: String) {
        val current = device.value
        val log = UsageLog(
            id = java.util.UUID.randomUUID().toString(),
            deviceId = current.id,
            action = action,
            timestamp = System.currentTimeMillis()
        )
        repository.logUsage(log)
    }
}
