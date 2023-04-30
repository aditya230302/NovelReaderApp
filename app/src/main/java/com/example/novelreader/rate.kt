package com.example.novelreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class rate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate)

        var toolbar = findViewById<Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Rate Us"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val simpleRatingBar = findViewById<RatingBar>(R.id.ratingbar)

        simpleRatingBar.setOnRatingBarChangeListener{ ratingBar, rating, fromUser ->
            Toast.makeText(this, "rating $rating, $fromUser",
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressed()
            Toast.makeText(this, "Returned Home", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
}