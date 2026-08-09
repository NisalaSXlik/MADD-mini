package com.example.smarthome.data.model

/**
 * Represents an individual controllable smart device or sensor in the home.
 *
 * @property id Unique identifier for the device.
 * @property floorId Identifier of the floor where the device is located.
 * @property name Human-readable name of the device.
 * @property type Category of the device defined by [DeviceType].
 * @property status Current operational status defined by [DeviceStatus].
 * @property gridX X-coordinate position on the floor plan grid.
 * @property gridY Y-coordinate position on the floor plan grid.
 * @property maxOnDurationMinutes Maximum allowed ON duration before safety-cutoff triggers automatically.
 * @property turnedOnAt Epoch timestamp (ms) when the device was last turned ON, used to calculate safety cutoff elapsed time.
 * @property scheduleStart Scheduled start time (e.g., "08:00") for automated light or device activation.
 * @property scheduleEnd Scheduled end time (e.g., "22:00") for automated device deactivation.
 * @property switchCount Number of individual channels/switches for multi-switch devices.
 * @property switchStates Map of individual switch identifiers to their boolean ON/OFF states.
 * @property cameraSnapshotUrl URL pointing to the latest snapshot image for camera devices.
 */
data class Device(
    var id: String = "",
    var floorId: String = "",
    var name: String = "",
    var type: DeviceType = DeviceType.OUTLET,
    var status: DeviceStatus = DeviceStatus.OFF,
    var gridX: Int = 0,
    var gridY: Int = 0,
    var maxOnDurationMinutes: Int? = null,
    var turnedOnAt: Long? = null,
    var scheduleStart: String? = null,
    var scheduleEnd: String? = null,
    var switchCount: Int? = null,
    var switchStates: Map<String, Boolean>? = null,
    var cameraSnapshotUrl: String? = null
) {
    constructor() : this("", "", "", DeviceType.OUTLET, DeviceStatus.OFF, 0, 0, null, null, null, null, null, null, null)
}
