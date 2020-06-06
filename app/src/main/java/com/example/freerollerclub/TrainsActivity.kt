package com.example.freerollerclub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class TrainsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trains)
    }

    fun onButton1(view: View) {
        val intent = Intent(this, ListOfTriks::class.java)
        intent.putExtra("style","SLALOM")
        startActivity(intent)
    }
    fun onButton2(view: View) {
        val intent = Intent(this, ListOfTriks::class.java)
        intent.putExtra("style","SLIDE")
        startActivity(intent)
    }
    fun onButton3(view: View) {
        val intent = Intent(this, ListOfTriks::class.java)
        intent.putExtra("style","JUMP")
        startActivity(intent)
    }
    fun onButton4(view: View) {
        val intent = Intent(this, ListOfTriks::class.java)
        intent.putExtra("style","AGGRESSIV")
        startActivity(intent)
    }
}
