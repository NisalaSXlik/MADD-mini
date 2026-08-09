package com.example.minimalapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : BaseActivity() {

    private val tag = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate - Home screen added to back stack")
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnWorkout).setOnClickListener {
            Log.d(tag, "Navigating Home -> Workout")
            startActivity(Intent(this, WorkoutActivity::class.java))
        }

        findViewById<Button>(R.id.btnSummary).setOnClickListener {
            Log.d(tag, "Navigating Home -> Summary")
            val intent = Intent(this, SummaryActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
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
        Log.d(tag, "onDestroy - Home removed from back stack")
    }
}
