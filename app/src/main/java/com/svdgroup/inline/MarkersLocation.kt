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
    private var markerContent = ""

    fun getDataFromDatabase(mMap: GoogleMap){
        database = AppDatabase.getDatabase()!!.getReference(citiesTitle)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    markerContent = "Points"
                    for (childSnapshot in ds.child(markersTitle).child(markerContent).children) {
                        markers(mMap, childSnapshot, markerContent)
                    }
                    markerContent = "Shops"
                    for (childSnapshot in ds.child(markersTitle).child(markerContent).children) {
                        markers(mMap, childSnapshot, markerContent)
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

fun markers(mMap: GoogleMap, childSnapshot: DataSnapshot, markerContent: String){
    val lat = childSnapshot.child("lat").getValue(String::class.java)
    val lon = childSnapshot.child("lon").getValue(String::class.java)
    val name = childSnapshot.child("name").getValue(String::class.java)
    val options = childSnapshot.child("options").getValue(String::class.java)

    val marker = LatLng(lat!!.toDouble(), lon!!.toDouble())

    if(markerContent == "Points") {
        mMap.addMarker(MarkerOptions().position(marker).title(name).snippet(options))
    }else{
        mMap.addMarker(
            MarkerOptions().position(marker).title(name).snippet(options).icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)
            )
        )
    }
}