package com.example.smarthome.data.repository

import android.util.Log
import com.example.smarthome.data.model.Alert
import com.example.smarthome.data.model.Device
import com.example.smarthome.data.model.Floor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmartHomeRepository @Inject constructor() {
    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()

    init {
        val currentUser = auth.currentUser
        Log.d("SmartHomeRepo", "Firebase Auth currentUser: ${currentUser?.uid}")
        if (currentUser == null) {
            auth.signInAnonymously()
                .addOnSuccessListener { result ->
                    Log.d("SmartHomeRepo", "Anonymous auth success: ${result.user?.uid}")
                }
                .addOnFailureListener { e ->
                    Log.e("SmartHomeRepo", "Anonymous auth failed", e)
                }
        }
    }

    fun observeFloors(): Flow<List<Floor>> = callbackFlow {
        val ref = database.getReference("floors")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Floor>()
                for (child in snapshot.children) {
                    val floor = child.getValue(Floor::class.java)
                    if (floor != null) {
                        list.add(floor)
                    }
                }
                Log.d("SmartHomeRepo", "observeFloors fetched ${list.size} floors from DB")
                trySend(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("SmartHomeRepo", "observeFloors cancelled", error.toException())
            }
        }
        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }

    fun observeDevices(): Flow<List<Device>> = callbackFlow {
        val ref = database.getReference("devices")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Device>()
                for (child in snapshot.children) {
                    val device = child.getValue(Device::class.java)
                    if (device != null) {
                        list.add(device)
                    }
                }
                Log.d("SmartHomeRepo", "observeDevices fetched ${list.size} devices from DB")
                trySend(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("SmartHomeRepo", "observeDevices cancelled", error.toException())
            }
        }
        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }

    fun addFloor(floor: Floor) {
        Log.d("SmartHomeRepo", "Adding floor ${floor.id} to DB")
        database.getReference("floors").child(floor.id).setValue(floor)
            .addOnSuccessListener { Log.d("SmartHomeRepo", "Successfully added floor ${floor.id}") }
            .addOnFailureListener { e -> Log.e("SmartHomeRepo", "Failed to add floor ${floor.id}", e) }
    }

    fun updateDevice(device: Device) {
        Log.d("SmartHomeRepo", "Updating device ${device.id} in DB")
        database.getReference("devices").child(device.id).setValue(device)
            .addOnSuccessListener { Log.d("SmartHomeRepo", "Successfully updated device ${device.id}") }
            .addOnFailureListener { e -> Log.e("SmartHomeRepo", "Failed to update device ${device.id}", e) }
    }
}
