package com.example.smarthome.data.repository

import android.util.Log
import com.example.smarthome.data.model.Alert
import com.example.smarthome.data.model.UsageLog
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

    fun observeAlerts(): Flow<List<Alert>> = callbackFlow {
        val ref = database.getReference("alerts")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Alert>()
                for (child in snapshot.children) {
                    val alert = child.getValue(Alert::class.java)
                    if (alert != null) {
                        list.add(alert)
                    }
                }
                trySend(list.sortedByDescending { it.timestamp })
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("SmartHomeRepo", "observeAlerts cancelled", error.toException())
            }
        }
        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }

    fun observeUsageLogs(): Flow<List<UsageLog>> = callbackFlow {
        val ref = database.getReference("usageLogs")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<UsageLog>()
                for (child in snapshot.children) {
                    val log = child.getValue(UsageLog::class.java)
                    if (log != null) {
                        list.add(log)
                    }
                }
                trySend(list.sortedByDescending { it.timestamp })
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("SmartHomeRepo", "observeUsageLogs cancelled", error.toException())
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

    fun updateFloor(floor: Floor) {
        Log.d("SmartHomeRepo", "Updating floor ${floor.id} in DB")
        database.getReference("floors").child(floor.id).setValue(floor)
            .addOnSuccessListener { Log.d("SmartHomeRepo", "Successfully updated floor ${floor.id}") }
            .addOnFailureListener { e -> Log.e("SmartHomeRepo", "Failed to update floor ${floor.id}", e) }
    }

    fun deleteFloor(floorId: String) {
        Log.d("SmartHomeRepo", "Deleting floor $floorId and its devices from DB")
        val updates = mutableMapOf<String, Any?>("floors/$floorId" to null)
        database.getReference("devices").get()
            .addOnSuccessListener { snapshot ->
                snapshot.children.forEach { child ->
                    val device = child.getValue(Device::class.java)
                    if (device?.floorId == floorId) {
                        updates["devices/${child.key}"] = null
                    }
                }
                database.getReference().updateChildren(updates)
            }
            .addOnFailureListener { e -> Log.e("SmartHomeRepo", "Failed to prepare floor delete $floorId", e) }
    }

    fun addDevice(device: Device) {
        Log.d("SmartHomeRepo", "Adding device ${device.id} to DB")
        database.getReference("devices").child(device.id).setValue(device)
            .addOnSuccessListener { Log.d("SmartHomeRepo", "Successfully added device ${device.id}") }
            .addOnFailureListener { e -> Log.e("SmartHomeRepo", "Failed to add device ${device.id}", e) }
    }

    fun updateDevice(device: Device) {
        Log.d("SmartHomeRepo", "Updating device ${device.id} in DB: ${device.name} -> status=${device.status}")
        database.getReference("devices").child(device.id).setValue(device)
            .addOnSuccessListener { Log.d("SmartHomeRepo", "Successfully updated device ${device.id}") }
            .addOnFailureListener { e -> Log.e("SmartHomeRepo", "Failed to update device ${device.id}", e) }
    }

    fun deleteDevice(deviceId: String) {
        Log.d("SmartHomeRepo", "Deleting device $deviceId from DB")
        database.getReference("devices").child(deviceId).removeValue()
            .addOnSuccessListener { Log.d("SmartHomeRepo", "Successfully deleted device $deviceId") }
            .addOnFailureListener { e -> Log.e("SmartHomeRepo", "Failed to delete device $deviceId", e) }
    }

    fun acknowledgeAlert(alertId: String) {
        database.getReference("alerts").child(alertId).child("acknowledged").setValue(true)
    }

    fun logUsage(log: UsageLog) {
        database.getReference("usageLogs").push().setValue(log)
    }
}
