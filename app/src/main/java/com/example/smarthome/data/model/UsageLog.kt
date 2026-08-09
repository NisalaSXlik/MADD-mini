package com.example.smarthome.data.model

/**
 * Represents an historical usage or audit entry for device activity.
 *
 * @property id Unique identifier for the usage log entry.
 * @property deviceId Identifier of the device associated with this log.
 * @property timestamp Epoch timestamp (ms) when the action occurred.
 * @property action Description of the action performed (e.g., "TURNED_ON", "SAFETY_CUTOFF", "SCHEDULE_TRIGGERED").
 * @property durationMinutes Duration in minutes the device remained active for this session.
 */
data class UsageLog(
    var id: String = "",
    var deviceId: String = "",
    var timestamp: Long = 0L,
    var action: String = "",
    var durationMinutes: Int = 0
) {
    constructor() : this("", "", 0L, "", 0)
}
