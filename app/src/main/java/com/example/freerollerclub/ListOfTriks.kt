package com.example.freerollerclub

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class ListOfTriks : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private var trains_name = "Trains"
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_triks)

        val intent_for_trik = Intent(this, SomeTrik::class.java)
        val style = intent.getStringExtra("style")
        val adapter = ArrayAdapter<String>(this, R.layout.listview_item)
        val listView: ListView = findViewById(R.id.listview_1)

        database = FirebaseDatabase.getInstance().getReference(trains_name).child(style)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val name = ds.child("name").getValue(String::class.java)
                    adapter.add(name.toString())
                }
                listView.setAdapter(adapter)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(ContentValues.TAG, databaseError.getMessage()) //Don't ignore errors!
            }
        }

        database.addListenerForSingleValueEvent(valueEventListener)

        listView.onItemClickListener = object : AdapterView.OnItemClickListener{

            override fun onItemClick(parent: AdapterView<*>, view: View,
                                     position: Int, id: Long) {

                // value of item that is clicked
                val itemValue = listView.getItemAtPosition(position) as String

                // Toast the values
                intent_for_trik.putExtra("style", style)
                intent_for_trik.putExtra("trik", itemValue)
                startActivity(intent_for_trik)
            }
        }


    }
}
