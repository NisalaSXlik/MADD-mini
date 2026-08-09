package com.example.minimalapp

import android.widget.Toast
import androidx.activity.ComponentActivity

open class BaseActivity : ComponentActivity() {

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "onResume called", Toast.LENGTH_SHORT).show()
    }
}
