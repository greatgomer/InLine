package com.svdgroup.inline

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_trick.*

class TrickActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private val trainsName = "Trains"
    val fieldsOrder = arrayOf("complexity", "description", "link_video")
    private lateinit var style : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trick)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        style = intent.getStringExtra("style")
        val trick = intent.getStringExtra("trick")
        val adapter = ArrayAdapter<String>(this, R.layout.listview_item)
        val listView: ListView = findViewById(R.id.listview_trick)

        database = AppDatabase.getDatabase()!!.getReference(trainsName).child(style).child(trick)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (fieldName in fieldsOrder) {
                    val name = dataSnapshot.child(fieldName).getValue(String::class.java)
                    adapter.add(name.toString())
                }
                val title = dataSnapshot.child("name").getValue(String::class.java)
                val imageLink = dataSnapshot.child("link_picture").getValue(String::class.java)
                supportActionBar!!.title = title
                listView.adapter = adapter

                Glide.with(applicationContext).load(imageLink).into(imageView)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(ContentValues.TAG, databaseError.message) //Don't ignore errors!
            }
        }

        database.addListenerForSingleValueEvent(valueEventListener)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // app icon in action bar clicked; go home
                val intent = Intent(this, ListOfTricksActivity::class.java)
                intent.putExtra("style", style)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}