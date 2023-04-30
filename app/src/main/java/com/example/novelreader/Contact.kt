package com.example.novelreader

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.RemoteInput
import android.app.TimePickerDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.MenuItem
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.Calendar

class Contact : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    lateinit var name : EditText
    lateinit var email : EditText
    lateinit var datetimebtn :Button
    lateinit var datetime :TextView
    lateinit var problem :EditText
    lateinit var submit :Button
    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    lateinit var pendingIntent: PendingIntent
    lateinit var soundUri: Uri
    lateinit var audioAttr: AudioAttributes
    lateinit var remoteInput: RemoteInput
    private val channelId = "My Channel ID"
    private val description = "We have recieved your mail"
    private val title="From Novel Reader"

    val myKey = "Remote Key"
    val notificationId =1234


    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        var toolbar = findViewById<Toolbar>(R.id.toolbar3)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Contact Us"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        datetimebtn = findViewById(R.id.datetimebtn)
        datetime = findViewById(R.id.datetime)
        problem = findViewById(R.id.probleminput)
        submit = findViewById(R.id.Submit)

        datetimebtn.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this, this, year, month,day)
            datePickerDialog.show()
        }
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        submit.setOnClickListener{
            val intent = Intent(this, NotificationViewExample::class.java)
            pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_MUTABLE)

            soundUri = Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+
                        applicationContext.packageName+"/"+R.raw.ringtone)
            audioAttr = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            // remoteCollapsedViews = RemoteViews(packageName, R.layout.activity_splash_screen_example)
            //remoteExpandedViews = RemoteViews(packageName, R.layout.activity_splash_screen_example_main)
            myNotificationChannel()

            remoteInput = RemoteInput.Builder(myKey).setLabel("Replying...")
                .build()
            val action: Notification.Action =
                Notification.Action.Builder(R.drawable.check, "Reply", pendingIntent)
                    .addRemoteInput(remoteInput).build()
            builder.addAction(action)



            notificationManager.notify(notificationId,builder.build())


        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun myNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            run {
                notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationChannel.setSound(soundUri, audioAttr)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this, channelId)
                    .setSmallIcon(R.drawable.a)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,android.R.drawable.ic_lock_idle_lock))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

            }

        else
            run {
                builder = Notification.Builder(this, channelId)
                    .setSmallIcon(android.R.drawable.btn_star)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            this.resources,
                            android.R.drawable.ic_lock_idle_lock
                        )
                    )
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }



    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = day
        myYear = year
        myMonth = month
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this, this, hour, minute,
            DateFormat.is24HourFormat(this))
        timePickerDialog.show()
    }
    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        datetime.text = "Date: $myDay,$myMonth,$myYear and Time:$myHour:$myMinute"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressed()
            Toast.makeText(this, "Returned Home", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
}