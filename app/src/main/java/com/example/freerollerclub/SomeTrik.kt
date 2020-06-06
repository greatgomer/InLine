package com.example.freerollerclub

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.*

class SomeTrik : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private val trains_name = "Trains"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_some_trik)

        val style = intent.getStringExtra("style")
        val trik = intent.getStringExtra("trik")
        var trik_data_list = arrayOf<String>()
        val adapter = ArrayAdapter<String>(this, R.layout.listview_item)
        val listView: ListView = findViewById(R.id.listview_2)


        database = FirebaseDatabase.getInstance().getReference(trains_name).child(style).child(trik)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d(ContentValues.TAG, dataSnapshot.key)
                for (ds in dataSnapshot.children) {
                    val name = ds.getValue(String::class.java)
                    trik_data_list += name.toString()
                    Log.d(ContentValues.TAG, "SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS")
                    Log.d(ContentValues.TAG, trik_data_list.last())
                }
                listView.setAdapter(adapter)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(ContentValues.TAG, databaseError.getMessage()) //Don't ignore errors!
            }
        }

        database.addListenerForSingleValueEvent(valueEventListener)

    }
}
