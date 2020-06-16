package com.svdgroup.inline

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*

class MarkersLocation {
    private lateinit var database: DatabaseReference
    private val citiesTitle = "Cities"
    private val markersTitle = "Markers"

    fun getDataFromDatabase(mMap: GoogleMap){
        database = AppDatabase.getDatabase()!!.getReference(citiesTitle)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    for (childSnapshot in ds.child(markersTitle).child("Points").children) {
                        markers(mMap, childSnapshot)
                    }

                    for (childSnapshot in ds.child(markersTitle).child("Shops").children) {
                        markers(mMap, childSnapshot)
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

fun markers(mMap: GoogleMap, childSnapshot: DataSnapshot){
    val lat = childSnapshot.child("lat").getValue(String::class.java)
    val lon = childSnapshot.child("lon").getValue(String::class.java)
    val name = childSnapshot.child("name").getValue(String::class.java)
    val options = childSnapshot.child("options").getValue(String::class.java)

    val marker = LatLng(lat!!.toDouble(), lon!!.toDouble())
    mMap.addMarker(MarkerOptions().position(marker).title(name).snippet(options).icon(
        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)))
}