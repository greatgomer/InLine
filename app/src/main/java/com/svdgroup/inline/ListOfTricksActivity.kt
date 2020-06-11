package com.svdgroup.inline

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class ListOfTricksActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private var trainsName = "Trains"
    private var tricks = mutableMapOf<String, String?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_tricks)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val style = intent.getStringExtra("style")
        val adapter = ArrayAdapter<String>(this, R.layout.listview_item)
        val listView: ListView = findViewById(R.id.listviewTricks)

        supportActionBar!!.title = style

        database = AppDatabase.getDatabase()!!.getReference(trainsName).child(style)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {
                    val name = childSnapshot.child("name").getValue(String::class.java)
                    adapter.add(name.toString())
                    tricks[name.toString()] = childSnapshot.key
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
                intentForTrick.putExtra("style", style)
                intentForTrick.putExtra("trick", tricks[itemValue])
                startActivity(intentForTrick)
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // app icon in action bar clicked; go home
                val intent = Intent(this, TrainsActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}