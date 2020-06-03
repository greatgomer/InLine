package com.example.freerollerclub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.location.*

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var mFusedLocationClient: FusedLocationProviderClient
    val readFromFirebase = ReadFromFirebase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        this.supportActionBar?.hide()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

        override fun onMapReady(googleMap: GoogleMap) {
            MyLocation(this, mFusedLocationClient, googleMap).getLastLocation()
            readFromFirebase.getDataFromDatabase(googleMap)
    }
}