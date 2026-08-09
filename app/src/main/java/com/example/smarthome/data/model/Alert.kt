package com.example.smarthome.data.model

/**
 * Represents a security or safety notification triggered by a device (e.g., safety-cutoff activation).
 *
 * @property id Unique identifier for the alert.
 * @property deviceId Identifier of the device that triggered the alert.
 * @property message Descriptive alert message explaining the safety or warning event.
 * @property timestamp Epoch timestamp (ms) when the alert was triggered.
 * @property acknowledged Flag indicating whether the user has reviewed and acknowledged the alert.
 */
data class Alert(
    var id: String = "",
    var deviceId: String = "",
    var message: String = "",
    var timestamp: Long = 0L,
    var acknowledged: Boolean = false
) {
    constructor() : this("", "", "", 0L, false)
}
