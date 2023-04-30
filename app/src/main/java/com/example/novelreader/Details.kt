package com.example.novelreader

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.squareup.picasso.Picasso

class Details : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val sTitle = intent.getStringExtra("storyTitle")
        val sContent = intent.getStringExtra("storyContent")
        val sImage = intent.getStringExtra("storyImage")
        var toolbar = findViewById<Toolbar>(R.id.toolbar1)
        setSupportActionBar(toolbar)

        supportActionBar?.title = sTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var storyFeatureImage = findViewById<ImageView>(R.id.storyFeatureImage)

        Picasso.get().load(sImage).into(storyFeatureImage)

        var storyDetails = findViewById<TextView>(R.id.storyDetails)
        storyDetails.text = sContent
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressed()
            Toast.makeText(this, "Returned Home", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
}