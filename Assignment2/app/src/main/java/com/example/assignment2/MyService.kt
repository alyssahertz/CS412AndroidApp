package com.example.assignment2

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MyService : Service() {

    override fun onCreate() {
        super.onCreate()

        val channel = NotificationChannel(
            "MY_SERVICE",
            "My Service",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = Notification.Builder(
            this,
            "MY_SERVICE"
        )
            .setContentTitle("CSCI 412 Android App")
            .setContentText("The service has started.")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        startForeground(1, notification)

        return START_STICKY
    }

    private val binder = MyBinder()

    inner class MyBinder : Binder() {
        fun getService(): MyService {
            return this@MyService
        }
    }

    fun getMyGrade(): String{
        return "A"
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}