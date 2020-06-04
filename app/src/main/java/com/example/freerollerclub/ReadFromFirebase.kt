package com.example.freerollerclub

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*

class ReadFromFirebase {
    private lateinit var database: DatabaseReference
    private var places_name = "Places"
    val city = ArrayList<String>(1)

    fun getDataFromDatabase(mMap: GoogleMap) {
        city.add("Rostov")
        city.add("Moscow")
        for (i in city) {
            database = FirebaseDatabase.getInstance().getReference(places_name).child(i)
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (ds in dataSnapshot.children) {
                        val lat = ds.child("lat").getValue(String::class.java)
                        val lon = ds.child("lon").getValue(String::class.java)
                        val name = ds.child("name").getValue(String::class.java)
                        val options = ds.child("options").getValue(String::class.java)

                        val marker = LatLng(lat!!.toDouble(), lon!!.toDouble())
                        mMap.addMarker(MarkerOptions().position(marker).title(name).snippet(options))

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(TAG, databaseError.getMessage()) //Don't ignore errors!
                }
            }
            database.addListenerForSingleValueEvent(valueEventListener)
        }
    }
}