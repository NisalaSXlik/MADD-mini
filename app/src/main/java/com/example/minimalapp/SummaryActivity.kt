package com.example.minimalapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class SummaryActivity : BaseActivity() {

    private val tag = "SummaryActivity"
    private lateinit var emailEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate - Summary screen added to back stack")
        setContentView(R.layout.activity_summary)

        emailEditText = findViewById(R.id.emailEditText)
        emailEditText.setText(savedInstanceState?.getString("email") ?: "")

        findViewById<Button>(R.id.btnHome).setOnClickListener {
            Log.d(tag, "Navigating Summary -> Home")
            startActivity(Intent(this, MainActivity::class.java))
        }

        findViewById<Button>(R.id.btnWorkout).setOnClickListener {
            Log.d(tag, "Navigating Summary -> Workout")
            startActivity(Intent(this, WorkoutActivity::class.java))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("email", emailEditText.text.toString())
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
        Log.d(tag, "onDestroy - Summary removed from back stack")
    }
}
