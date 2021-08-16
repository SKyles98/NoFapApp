package com.saleef.temperedvolition

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent


import androidx.fragment.app.FragmentActivity
import java.util.*

// This will be our utility class to assist with alarm function (Checking Alarm State,Activating Alarm,Canceling Alarm)
class AlarmHelper(private val fragmentActivity: FragmentActivity,private val sharedPrefs: SharedPrefs) {

     // Monitors state of the alarm if user on the app then we stop notifs and
      enum class TimerState {
          STARTED,STOPPED,Paused
      }



    fun initAlarmTimer(){
            //86400000
            // We start the alarm because it hasnt ran yet
            // To start the alarm we need a calendarInstance
        if (sharedPrefs.getTimerState() == TimerState.STOPPED) {
            val calendar: Calendar = Calendar.getInstance()
            val am: AlarmManager = fragmentActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(fragmentActivity, AlertReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(fragmentActivity, 1, intent, 0)
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis + 86400000, 30 * 1000, pendingIntent) //24hrs
            sharedPrefs.saveStartTime(calendar.timeInMillis) // Saved when our streak was started
            sharedPrefs.setTimerState(TimerState.STARTED)
        }
    }


    fun cancelAlarmTimer(){
        val am:AlarmManager = fragmentActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(fragmentActivity,AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(fragmentActivity,1,intent,0)
        am.cancel(pendingIntent)
    }



    fun getRemainingTimerDuration():Long{
        //Gets our remaining time before the alarm goes off
        return  86400 - (System.currentTimeMillis() - sharedPrefs.getStartTime())/1000
//
    }

    // Gets the time to start off on for displaying
    fun getStartingTimeOffset():Long{
       return  (System.currentTimeMillis() - sharedPrefs.getStartTime())/1000
    }

    fun twentyFourHours():Int{
        return 86400
    }
     companion object{
         const val CHANNEL_ID = "NOFAPCHANNEL"
         const val Notificationid = 101

     }

}