package com.example.smarthome.ui.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.model.Alert
import com.example.smarthome.data.model.UsageLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DeviceUsageSummary(
    val deviceId: String,
    val deviceName: String,
    val totalMinutes: Int
)

@HiltViewModel
class ReportsViewModel @Inject constructor() : ViewModel() {

    private val _alerts = MutableStateFlow<List<Alert>>(
        listOf(
            Alert(
                id = "alert_1",
                deviceId = "dev_1",
                message = "Iron Slot reached max ON duration safety cutoff.",
                timestamp = System.currentTimeMillis() - 3600000,
                acknowledged = false
            ),
            Alert(
                id = "alert_2",
                deviceId = "dev_3",
                message = "Master Hall Camera disconnected unexpectedly.",
                timestamp = System.currentTimeMillis() - 86400000,
                acknowledged = true
            )
        )
    )
    val alerts: StateFlow<List<Alert>> = _alerts.asStateFlow()

    private val _usageSummaries = MutableStateFlow<List<DeviceUsageSummary>>(
        listOf(
            DeviceUsageSummary(deviceId = "dev_1", deviceName = "Living Room Outlet", totalMinutes = 240),
            DeviceUsageSummary(deviceId = "dev_2", deviceName = "Kitchen Light", totalMinutes = 180),
            DeviceUsageSummary(deviceId = "dev_3", deviceName = "Master Hall Camera", totalMinutes = 420)
        )
    )
    val usageSummaries: StateFlow<List<DeviceUsageSummary>> = _usageSummaries.asStateFlow()

    fun acknowledgeAlert(alertId: String) {
        viewModelScope.launch {
            _alerts.value = _alerts.value.map { alert ->
                if (alert.id == alertId) alert.copy(acknowledged = true) else alert
            }
        }
    }
}
