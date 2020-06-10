package com.svdgroup.inline

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }

    fun onMap(view: View) {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

    fun onTrains(view: View) {
        val intent = Intent(this, TrainsActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_links_on_cities -> {
                val intent = Intent(this, RollersGroupsInCitiesActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_your_account -> {
//                val intent = Intent(this, MapsActivity::class.java)
//                startActivity(intent)
                return true
            }
            R.id.action_settings -> {
//                val intent = Intent(this, MapsActivity::class.java)
//                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}