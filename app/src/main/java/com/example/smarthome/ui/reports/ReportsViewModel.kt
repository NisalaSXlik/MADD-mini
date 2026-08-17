package com.example.smarthome.ui.reports

import androidx.lifecycle.ViewModel
import com.example.smarthome.data.model.Alert
import com.example.smarthome.data.repository.SmartHomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import javax.inject.Inject

data class DeviceUsageSummary(
    val deviceId: String,
    val deviceName: String,
    val totalMinutes: Int,
    val eventCount: Int,
    val lastAction: String
)

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val repository: SmartHomeRepository
) : ViewModel() {

    val alerts: StateFlow<List<Alert>> = repository.observeAlerts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val usageSummaries: StateFlow<List<DeviceUsageSummary>> = combine(
        repository.observeUsageLogs(),
        repository.observeDevices()
    ) { logs, devices ->
        val deviceNames = devices.associate { it.id to it.name }
        val sevenDaysAgo = System.currentTimeMillis() - 7L * 24L * 60L * 60L * 1000L

        logs
            .filter { it.timestamp >= sevenDaysAgo }
            .groupBy { it.deviceId }
            .map { (deviceId, deviceLogs) ->
                val sortedLogs = deviceLogs.sortedByDescending { it.timestamp }
                DeviceUsageSummary(
                    deviceId = deviceId,
                    deviceName = deviceNames[deviceId] ?: "Unknown Device",
                    totalMinutes = deviceLogs.sumOf { it.durationMinutes.coerceAtLeast(0) },
                    eventCount = deviceLogs.size,
                    lastAction = sortedLogs.firstOrNull()?.action ?: "NO_ACTIVITY"
                )
            }
            .sortedByDescending { it.totalMinutes }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun acknowledgeAlert(alertId: String) {
        repository.acknowledgeAlert(alertId)
    }
}
