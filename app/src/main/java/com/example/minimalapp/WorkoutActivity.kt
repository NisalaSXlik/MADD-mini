package com.example.minimalapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

class WorkoutActivity : BaseActivity() {

    private val tag = "WorkoutActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate - Workout screen added to back stack")

        setContent {
            MaterialTheme {
                WorkoutScreen(
                    onHomeClick = {
                        Log.d(tag, "Navigating Workout -> Home")
                        startActivity(Intent(this, MainActivity::class.java))
                    },
                    onSummaryClick = {
                        Log.d(tag, "Navigating Workout -> Summary")
                        val intent = Intent(this, SummaryActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    },
                    onConfirmExit = {
                        Log.d(tag, "Back confirmed - Workout removed from back stack")
                        finish()
                    }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy - Workout removed from back stack")
    }
}

@Composable
fun WorkoutScreen(
    onHomeClick: () -> Unit,
    onSummaryClick: () -> Unit,
    onConfirmExit: () -> Unit
) {
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler {
        showExitDialog = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Workout",
            fontSize = 28.sp
        )

        ResumeStateMessage()

        UserCard(
            name = "Chirath",
            status = "Available"
        )

        CounterButton()

        CenteredButtonList()

        Button(
            onClick = onHomeClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text("Go to Home")
        }

        Button(
            onClick = onSummaryClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Summary")
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Confirm Exit") },
            text = { Text("Are you sure you want to leave?") },
            confirmButton = {
                Button(onClick = onConfirmExit) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = { showExitDialog = false }) {
                    Text("No")
                }
            }
        )
    }
}

@Composable
fun CenteredButtonList() {
    Column(
        modifier = Modifier.padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { }) {
            Text("Button 1")
        }

        Button(onClick = { }) {
            Text("Button 2")
        }

        Button(onClick = { }) {
            Text("Button 3")
        }
    }
}

@Composable
fun CounterButton() {
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Count: $count")

        Button(onClick = { count++ }) {
            Text("Increase Count")
        }
    }
}

@Composable
fun UserCard(
    name: String,
    status: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name.first().toString(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 24.sp
                )
            }

            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = name,
                    fontSize = 20.sp
                )
                Text(text = status)
            }
        }
    }
}

@Composable
fun ResumeStateMessage() {
    val lifecycleOwner = LocalLifecycleOwner.current
    var isResumed by remember { mutableStateOf(false) }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> isResumed = true
                Lifecycle.Event.ON_PAUSE -> isResumed = false
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (isResumed) {
        Text("Activity is in onResume state")
    }
}
