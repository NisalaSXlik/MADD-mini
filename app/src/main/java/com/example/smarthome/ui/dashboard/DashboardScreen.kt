package com.example.smarthome.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smarthome.data.model.Floor

private const val MIN_GRID_SIZE = 2
private const val MAX_GRID_SIZE = 8

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onFloorClick: (String) -> Unit
) {
    val floors by viewModel.floors.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }
    var newFloorName by remember { mutableStateOf("") }
    var gridRows by remember { mutableStateOf("5") }
    var gridCols by remember { mutableStateOf("5") }
    var editingFloor by remember { mutableStateOf<Floor?>(null) }
    var deletingFloor by remember { mutableStateOf<Floor?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Smart Home Dashboard") },
                actions = {
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Floor")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "${floors.size} floors. Grid size: $MIN_GRID_SIZE x $MIN_GRID_SIZE to $MAX_GRID_SIZE x $MAX_GRID_SIZE.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            items(floors) { floor ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onFloorClick(floor.id) }
                        ) {
                            Text(text = floor.name, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Grid: ${floor.gridRows} x ${floor.gridCols}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        IconButton(onClick = { editingFloor = floor }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit floor")
                        }
                        IconButton(onClick = { deletingFloor = floor }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete floor")
                        }
                    }
                }
            }
        }

        if (showAddDialog) {
            FloorEditorDialog(
                title = "Add New Floor",
                initialName = newFloorName,
                initialRows = gridRows,
                initialCols = gridCols,
                onDismiss = { showAddDialog = false },
                onSave = { name, rows, cols ->
                    viewModel.addFloor(name, rows, cols)
                    newFloorName = ""
                    gridRows = "5"
                    gridCols = "5"
                    showAddDialog = false
                }
            )
        }

        editingFloor?.let { floor ->
            FloorEditorDialog(
                title = "Edit Floor",
                initialName = floor.name,
                initialRows = floor.gridRows.toString(),
                initialCols = floor.gridCols.toString(),
                onDismiss = { editingFloor = null },
                onSave = { name, rows, cols ->
                    viewModel.updateFloor(floor, name, rows, cols)
                    editingFloor = null
                }
            )
        }

        deletingFloor?.let { floor ->
            AlertDialog(
                onDismissRequest = { deletingFloor = null },
                title = { Text("Delete Floor") },
                text = { Text("Delete ${floor.name} and all devices placed on it?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteFloor(floor.id)
                            deletingFloor = null
                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { deletingFloor = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
private fun FloorEditorDialog(
    title: String,
    initialName: String,
    initialRows: String,
    initialCols: String,
    onDismiss: () -> Unit,
    onSave: (String, Int, Int) -> Unit
) {
    var name by remember(initialName) { mutableStateOf(initialName) }
    var rowsText by remember(initialRows) { mutableStateOf(initialRows) }
    var colsText by remember(initialCols) { mutableStateOf(initialCols) }
    val rows = rowsText.toIntOrNull()?.coerceIn(MIN_GRID_SIZE, MAX_GRID_SIZE) ?: 5
    val cols = colsText.toIntOrNull()?.coerceIn(MIN_GRID_SIZE, MAX_GRID_SIZE) ?: 5

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Floor Name") },
                    singleLine = true
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = rowsText,
                        onValueChange = { rowsText = it.filter(Char::isDigit).take(1) },
                        label = { Text("Rows") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = colsText,
                        onValueChange = { colsText = it.filter(Char::isDigit).take(1) },
                        label = { Text("Columns") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }
                Text(
                    text = "Saved grid will be ${rows} x ${cols}. Maximum visible grid is ${MAX_GRID_SIZE} x ${MAX_GRID_SIZE}.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank()) {
                        onSave(name.trim(), rows, cols)
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
