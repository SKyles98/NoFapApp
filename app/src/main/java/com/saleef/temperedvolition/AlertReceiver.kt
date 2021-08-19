package com.saleef.temperedvolition

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

// Receives our pending intent from the alarm
class AlertReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        createNotification(context)
   val sharedPrefs: SharedPrefs? =
       context?.getSharedPreferences(R.string.Preferences_File_Key.toString(),Context.MODE_PRIVATE)
           ?.let { SharedPrefs(it) }
        sharedPrefs?.incrementDay(1)
        // Each day passed we increment the start by 24 hours in milliseconds

        sharedPrefs?.saveStartTime(sharedPrefs.getStartTime() + 86400000)
        if (sharedPrefs != null) {
            sendNotification(context,sharedPrefs.getDays())
        }

    }

    fun createNotification(context: Context?){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            val name = "Day Passed"
            val descriptionText = "Dont Give Up"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(AlarmHelper.CHANNEL_ID, name, importance).apply { description = descriptionText }
            val notificationManager: NotificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

    fun sendNotification(context: Context?,day:Int){
        // Let function essentially says if the variale is not null then perform functionality
        val builder = context?.let {
            NotificationCompat.Builder(it, AlarmHelper.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Day$day")
                .setContentText("Keep Striving")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        }

        if (builder != null) {
            with(context.let { NotificationManagerCompat.from(it) }){
                this.notify(AlarmHelper.Notificationid,builder.build())
                    }
        }

    }
}