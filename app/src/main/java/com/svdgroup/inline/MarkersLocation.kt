package com.svdgroup.inline

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*

class MarkersLocation {
    private lateinit var database: DatabaseReference
    private var placesName = "Places"
    var markers = arrayOf<String>()

    fun getDataFromDatabase(mMap: GoogleMap){
        database = AppDatabase.getDatabase()!!.getReference(placesName)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val city = ds.key
                    markers += city.toString()
                    for (childSnapshot in ds.children) {
                        val lat = childSnapshot.child("lat").getValue(String::class.java)
                        val lon = childSnapshot.child("lon").getValue(String::class.java)
                        val name = childSnapshot.child("name").getValue(String::class.java)
                        val options = childSnapshot.child("options").getValue(String::class.java)

                        val marker = LatLng(lat!!.toDouble(), lon!!.toDouble())
                        mMap.addMarker(MarkerOptions().position(marker).title(name).snippet(options))
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(TAG, databaseError.message) //Don't ignore errors!
            }
        }
        database.addListenerForSingleValueEvent(valueEventListener)
    }
}