package com.example.freerollerclub

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class TrainsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trains)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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
