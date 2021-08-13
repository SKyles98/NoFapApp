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
     private lateinit var timer:CountUpTimer

    fun initAlarmTimer(){
            //86400000
            // We start the alarm because it hasnt ran yet
            // To start the alarm we need a calendarInstance
           val calendar:Calendar = Calendar.getInstance()
            val am:AlarmManager = fragmentActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(fragmentActivity,AlertReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(fragmentActivity,1,intent,0)
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis + (30*1000),30 * 1000,pendingIntent)
            sharedPrefs.saveStartTime(calendar.time.time) // Saved when our streak was started
            sharedPrefs.setTimerState(TimerState.STARTED)
    }


    fun cancelAlarmTimer(){
        val am:AlarmManager = fragmentActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(fragmentActivity,AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(fragmentActivity,1,intent,0)
        am.cancel(pendingIntent)
    }

   // Gets the last recorded time before user left the app or fragment
    fun saveTime(){
       sharedPrefs.saveEndTime(Calendar.getInstance().time.time)
    }

    fun calculateTimeDiff():Long{
        val backTime:Long = Calendar.getInstance().time.time
        val endTime:Long = sharedPrefs.getEndTime()
        return if (backTime >= endTime){
            backTime - endTime // Return the diff to display in the textview ( ex. textview would start at 4 hours)
        } else {
            // If the backtime is less than than we know the next day has started so we substract 24 hour from the  start time - backtime to
            // get the remaining hours need to finish the cycle
           return 86400000 - sharedPrefs.getStartTime() - backTime
        }
    }

     companion object{

         const val CHANNEL_ID = "NOFAPCHANNEL"
         const val Notificationid = 101
     }

}