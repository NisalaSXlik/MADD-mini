package com.example.smarthome.ui.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    viewModel: ReportsViewModel
) {
    val alerts by viewModel.alerts.collectAsStateWithLifecycle()
    val usageSummaries by viewModel.usageSummaries.collectAsStateWithLifecycle()

    val sortedUsage = usageSummaries.sortedByDescending { it.totalMinutes }
    val maxMinutes = sortedUsage.maxOfOrNull { it.totalMinutes }?.coerceAtLeast(1) ?: 1

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Safety Alerts & Usage Reports") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Alerts Section
            item {
                Text(text = "Active & Past Alerts", style = MaterialTheme.typography.titleLarge)
            }
            if (alerts.isEmpty()) {
                item {
                    Text(
                        text = "No alerts recorded yet.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            items(alerts) { alert ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (!alert.acknowledged) {
                            MaterialTheme.colorScheme.errorContainer
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        }
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = alert.message,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (!alert.acknowledged) {
                                MaterialTheme.colorScheme.onErrorContainer
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (alert.acknowledged) "Acknowledged" else "Unacknowledged",
                                style = MaterialTheme.typography.labelMedium
                            )
                            if (!alert.acknowledged) {
                                TextButton(onClick = { viewModel.acknowledgeAlert(alert.id) }) {
                                    Text("Mark Acknowledged")
                                }
                            }
                        }
                    }
                }
            }

            // Usage Report Section
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "7-Day Device Activity", style = MaterialTheme.typography.titleLarge)
            }
            if (sortedUsage.isEmpty()) {
                item {
                    Text(
                        text = "No usage activity logged in the last 7 days.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            items(sortedUsage) { summary ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = summary.deviceName, style = MaterialTheme.typography.titleMedium)
                            Text(text = "${summary.totalMinutes} mins", style = MaterialTheme.typography.bodyMedium)
                        }
                        Text(
                            text = "${summary.eventCount} events - Last: ${summary.lastAction}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        // Simple Bar representation
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(16.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.small)
                        ) {
                            val fraction = (summary.totalMinutes.toFloat() / maxMinutes).coerceIn(0f, 1f)
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(fraction)
                                    .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.small)
                            )
                        }
                    }
                }
            }
        }
    }
}
