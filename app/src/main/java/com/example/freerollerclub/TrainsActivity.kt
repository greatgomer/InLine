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

    fun onButtonSlalom(view: View) {
        val intent = Intent(this, ListOfTricksActivity::class.java)
        intent.putExtra("style","SLALOM")
        startActivity(intent)
    }
    fun onButtonSlide(view: View) {
        val intent = Intent(this, ListOfTricksActivity::class.java)
        intent.putExtra("style","SLIDE")
        startActivity(intent)
    }
    fun onButtonJump(view: View) {
        val intent = Intent(this, ListOfTricksActivity::class.java)
        intent.putExtra("style","JUMP")
        startActivity(intent)
    }
    fun onButtonAggressiv(view: View) {
        val intent = Intent(this, ListOfTricksActivity::class.java)
        intent.putExtra("style","AGGRESSIV")
        startActivity(intent)
    }
}
