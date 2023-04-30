package com.example.novelreader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import kotlin.random.Random

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var storyTitles = arrayOf<String>()
    var storyContents = arrayOf<String>()
    var storyImages = arrayOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        var drawerlayout = findViewById<DrawerLayout>(R.id.drawerlayout)

        val toggle = ActionBarDrawerToggle(this,drawerlayout,toolbar,R.string.open,R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        var navigationView = findViewById<NavigationView>(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)

        storyTitles = resources.getStringArray(R.array.storyTitles)
        storyContents = resources.getStringArray(R.array.storyContent)
        storyImages = resources.getStringArray(R.array.storyImages)

        val adapter = ItemAdapter(storyTitles,storyContents,storyImages)

        var storyList = findViewById<RecyclerView>(R.id.storyList)
        storyList.layoutManager = LinearLayoutManager(this)
        storyList.adapter = adapter
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var drawerlayout = findViewById<DrawerLayout>(R.id.drawerlayout)
        drawerlayout.closeDrawer(GravityCompat.START)
        if(item.itemId == R.id.random){
            val randomPosition = Random.nextInt(0,storyTitles.size)
            val intent = Intent(applicationContext,Details::class.java)
            intent.putExtra("storyTitle",storyTitles[randomPosition])
            intent.putExtra("storyContent",storyContents[randomPosition])
            intent.putExtra("storyImage",storyImages[randomPosition])
            startActivity(intent)
            Toast.makeText(this, "Opened Random Novel", Toast.LENGTH_LONG).show()

        }
        if(item.itemId == R.id.rate){
            val intent = Intent(applicationContext,rate::class.java)
            startActivity(intent)
            Toast.makeText(this, "Opened Rating section", Toast.LENGTH_LONG).show()
        }
        if(item.itemId == R.id.contact){
            val intent = Intent(applicationContext,Contact::class.java)
            startActivity(intent)
            Toast.makeText(this, "Opened Contacts Section", Toast.LENGTH_LONG).show()
        }

        return true
    }

}