package com.svdgroup.inline

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_trick.*

class TrickActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private val trainsName = "Trains"
    private lateinit var style : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trick)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        style = intent.getStringExtra("style")
        val trick = intent.getStringExtra("trick")
        val complexityView = findViewById<TextView>(R.id.textView_complexity)
        val descriptionView = findViewById<TextView>(R.id.textView_description)
        val linkVideoView = findViewById<TextView>(R.id.textView_link_video)

        database = AppDatabase.getDatabase()!!.getReference(trainsName).child(style).child(trick)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val title = dataSnapshot.child("name").getValue(String::class.java)
                supportActionBar!!.title = title
                val imageLink = dataSnapshot.child("link_picture").getValue(String::class.java)
                Glide.with(applicationContext).load(imageLink).into(imageView)
                val complexity = dataSnapshot.child("complexity").getValue(String::class.java)
                complexityView.text = complexity
                val description = dataSnapshot.child("description").getValue(String::class.java)
                descriptionView.text = description
                val linkVideo = dataSnapshot.child("link_video").getValue(String::class.java)
                linkVideoView.text = linkVideo
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