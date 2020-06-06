package com.example.freerollerclub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
  ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val readFromFirebase = MarkersLocation()
    private lateinit var currentLocation: CurrentLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        this.supportActionBar?.hide()
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        currentLocation = CurrentLocation(this, mFusedLocationClient, googleMap)
        currentLocation.setLastLocation()
        readFromFirebase.getDataFromDatabase(googleMap)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        currentLocation.setLastLocation()
    }
}