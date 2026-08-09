package com.example.smarthome.ui.dashboard

import androidx.lifecycle.ViewModel
import com.example.smarthome.data.model.Device
import com.example.smarthome.data.model.DeviceStatus
import com.example.smarthome.data.model.DeviceType
import com.example.smarthome.data.model.Floor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {

    private val _floors = MutableStateFlow<List<Floor>>(
        listOf(
            Floor(id = "floor_1", name = "Ground Floor", imageUrl = null, gridRows = 5, gridCols = 5),
            Floor(id = "floor_2", name = "First Floor", imageUrl = null, gridRows = 5, gridCols = 5)
        )
    )
    val floors: StateFlow<List<Floor>> = _floors.asStateFlow()

    private val _devices = MutableStateFlow<List<Device>>(
        listOf(
            Device(
                id = "dev_1",
                floorId = "floor_1",
                name = "Living Room Outlet",
                type = DeviceType.OUTLET,
                status = DeviceStatus.ON,
                gridX = 1,
                gridY = 2
            ),
            Device(
                id = "dev_2",
                floorId = "floor_1",
                name = "Kitchen Light",
                type = DeviceType.LIGHT_SCHEDULE,
                status = DeviceStatus.OFF,
                gridX = 3,
                gridY = 1
            ),
            Device(
                id = "dev_3",
                floorId = "floor_2",
                name = "Master Hall Camera",
                type = DeviceType.CAMERA,
                status = DeviceStatus.ERROR,
                gridX = 2,
                gridY = 2
            )
        )
    )
    val devices: StateFlow<List<Device>> = _devices.asStateFlow()

    fun getDevicesForFloor(floorId: String): List<Device> {
        return _devices.value.filter { it.floorId == floorId }
    }

    fun addFloor(name: String, gridRows: Int, gridCols: Int) {
        val newFloor = Floor(
            id = UUID.randomUUID().toString(),
            name = name,
            imageUrl = null,
            gridRows = gridRows,
            gridCols = gridCols
        )
        _floors.value = _floors.value + newFloor
    }
}
