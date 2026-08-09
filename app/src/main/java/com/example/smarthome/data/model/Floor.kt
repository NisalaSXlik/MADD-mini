package com.example.smarthome.data.model

/**
 * Represents a physical floor or zone in the smart home.
 *
 * @property id Unique identifier for the floor.
 * @property name Display name of the floor (e.g., "Ground Floor", "First Floor").
 * @property imageUrl Optional blueprint or floor plan image URL.
 * @property gridRows Number of rows in the floor's layout grid for device placement.
 * @property gridCols Number of columns in the floor's layout grid for device placement.
 */
data class Floor(
    var id: String = "",
    var name: String = "",
    var imageUrl: String? = null,
    var gridRows: Int = 0,
    var gridCols: Int = 0
) {
    constructor() : this("", "", null, 0, 0)
}
