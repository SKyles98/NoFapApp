package com.saleef.temperedvolition

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log


import androidx.fragment.app.FragmentActivity
import com.saleef.temperedvolition.DataBase.HistoryNote
import java.text.SimpleDateFormat
import java.util.*

// This will be our utility class to assist with alarm function (Checking Alarm State,Activating Alarm,Canceling Alarm)
class AlarmHelper(
    private val fragmentActivity: FragmentActivity,
    private val sharedPrefs: SharedPrefs
) {

     // Monitors state of the alarm if user on the app then we stop notifs and
      enum class TimerState {
          STARTED,STOPPED
      }

    private var dateFormat = "dd-MM-yyyy hh:mm"
    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat: SimpleDateFormat = SimpleDateFormat(dateFormat)

    fun initAlarmTimer(){

        if (sharedPrefs.getTimerState() == TimerState.STOPPED) {
            val calendar: Calendar = Calendar.getInstance()
            val am: AlarmManager = fragmentActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(fragmentActivity, AlertReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(fragmentActivity, 1, intent, 0)
            am.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis + 86400000,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            ) //24hrs
            val date:String = simpleDateFormat.format(calendar.time)
            Log.i("Data",date)
            sharedPrefs.saveStartTime(calendar.timeInMillis) // Saved when our streak was started
            sharedPrefs.setStartDate(date)
            sharedPrefs.setTimerState(TimerState.STARTED)
        }
    }


    private fun cancelAlarmTimer(){
        val am:AlarmManager = fragmentActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(fragmentActivity, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(fragmentActivity, 1, intent, 0)
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

    fun getNote(note: String): HistoryNote {
        val startDate: String = sharedPrefs.getStartDate() + "->"
        val c:Calendar = Calendar.getInstance()
        val endDate:String = simpleDateFormat.format(c.time)
        val duration:Int = sharedPrefs.getDays()
        return HistoryNote(0, startDate, endDate, duration, note)
    }

    fun resetAlarm(){
        cancelAlarmTimer()
        sharedPrefs.setTimerState(TimerState.STOPPED)
        // NO point in changing start status as alarm would already be init
        initAlarmTimer()
    }

    companion object{
         const val CHANNEL_ID = "NOFAPCHANNEL"
         const val Notificationid = 101
     }

}