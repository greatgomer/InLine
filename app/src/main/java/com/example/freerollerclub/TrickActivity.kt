package com.example.freerollerclub

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.*

class TrickActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private val trainsName = "Trains"
    val fieldsOrder = arrayOf("name", "link_picture", "complexity", "description", "link_video")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trick)

        val style = intent.getStringExtra("style")
        val trick = intent.getStringExtra("trick")
        val adapter = ArrayAdapter<String>(this, R.layout.listview_item)
        val listView: ListView = findViewById(R.id.listview_trick)


        database = FirebaseDatabase.getInstance().getReference(trainsName).child(style).child(trick)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (fieldName in fieldsOrder) {
                    val name = dataSnapshot.child(fieldName).getValue(String::class.java)
                    adapter.add(name.toString())
                }
                listView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(ContentValues.TAG, databaseError.message) //Don't ignore errors!
            }
        }

        database.addListenerForSingleValueEvent(valueEventListener)

    }
}
