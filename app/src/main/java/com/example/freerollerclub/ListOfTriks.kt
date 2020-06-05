package com.example.freerollerclub

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class ListOfTriks : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private var trains_name = "Trains"
    var trains_list: MutableList<String> = mutableListOf<String>()
    private lateinit var listView: ListView
    var array = arrayOf("Melbourne", "Vienna", "Vancouver", "Toronto", "Calgary", "Adelaide", "Perth", "Auckland", "Helsinki", "Hamburg", "Munich", "New York", "Sydney", "Paris", "Cape Town", "Barcelona", "London", "Bangkok")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_triks)

        database = FirebaseDatabase.getInstance().getReference(trains_name).child("FSK")
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val name = ds.child("name").getValue(String::class.java)
                    trains_list.add(name.toString())
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(ContentValues.TAG, databaseError.getMessage()) //Don't ignore errors!
            }
        }

        database.addListenerForSingleValueEvent(valueEventListener)

        val adapter = ArrayAdapter(this,
            R.layout.listview_item, array)

        val listView: ListView = findViewById(R.id.listview_1)
        listView.setAdapter(adapter)
    }
}
