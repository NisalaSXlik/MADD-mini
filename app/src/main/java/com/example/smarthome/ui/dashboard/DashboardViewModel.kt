package com.example.smarthome.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.model.Device
import com.example.smarthome.data.model.Floor
import com.example.smarthome.data.repository.SmartHomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

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
            gridRows = gridRows,
            gridCols = gridCols
        )
        repository.addFloor(newFloor)
    }
}
