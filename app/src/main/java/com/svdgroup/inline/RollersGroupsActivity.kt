package com.svdgroup.inline

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class RollersGroupsActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private var cities = "Cities"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rollers_groups)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val adapter = ArrayAdapter<String>(this, R.layout.listview_item)
        val listView: ListView = findViewById(R.id.listviewGroups)

        database = AppDatabase.getDatabase()!!.getReference(cities)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val name = ds.child("name_en").getValue(String::class.java)
                    val city = Pair(ds.key.toString(), name.toString())
                    adapter.add(city.toString())
                    for (childSnapshot in ds.child("Links").children) {
                        val link = childSnapshot.child("Links").getValue(String::class.java)
                        adapter.add(link.toString())
                    }
                }
                listView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(ContentValues.TAG, databaseError.message) //Don't ignore errors!
            }
        }
        database.addListenerForSingleValueEvent(valueEventListener)

        val intentForTrick = Intent(this, TrickActivity::class.java)
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val itemValue = listView.getItemAtPosition(position) as String
                startActivity(intentForTrick)
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // app icon in action bar clicked; go home
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}