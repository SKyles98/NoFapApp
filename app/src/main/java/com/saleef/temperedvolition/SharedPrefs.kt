package com.saleef.temperedvolition

import android.content.SharedPreferences
import android.util.Log

class SharedPrefs(private val sharedPreferences: SharedPreferences) {


    //Store the highest day
    //Store the day for the current streak
    //Store the seconds(happens when user clicks on app

    // Method that recieves a one or a zero to notify that a service has been started
    // If 1 then that service has already been started so we dont start it again in fragment onCreate
    fun setStartStatus(started:Boolean){
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(START_STATUS,started)
        editor.apply()
    }

    fun getStartStatus():Boolean{
       return sharedPreferences.getBoolean(START_STATUS,false)
    }



    // Day is passed increment by one
    fun incrementDay(day: Int) {
      val editor:SharedPreferences.Editor = sharedPreferences.edit()
        val currDays:Int = sharedPreferences.getInt(CURRENT_DAYS,0)
        if (currDays == 0){
            editor.putInt(CURRENT_DAYS,day)
        } else{
            editor.putInt(CURRENT_DAYS,currDays + day)
        }
        editor.apply()
    }

    fun getDays(): Int {
       return sharedPreferences.getInt(CURRENT_DAYS,0)
    }

    fun setTimerState(state:AlarmHelper.TimerState){
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(TIMER_STATE,state.ordinal)
        editor.apply()
    }

    fun getTimerState():AlarmHelper.TimerState{
        // Converts our int  back to enum object
        return AlarmHelper.TimerState.values()[sharedPreferences.getInt(TIMER_STATE,1)]
    }

    fun saveStartTime(startingStreakTime: Long) {
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putLong(START_TIME,startingStreakTime)
        editor.apply()
    }

    fun getStartTime():Long{
        return sharedPreferences.getLong(START_TIME,0)
    }

    fun saveEndTime(time: Long){
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putLong(END_TIME,time)
        Log.i("saved?",time.toString())
        editor.apply()
    }

    fun getEndTime():Long{
        return sharedPreferences.getLong(END_TIME,0)
    }
    companion object{
        const val START_STATUS:String = "START STATUS"
        const val END_TIME:String = "CURRENT TIME"
        const val CURRENT_DAYS:String = "CURR_DAYS"
        const val TIMER_STATE:String = "TIMER STATE"
        const val START_TIME:String = "START TIME"
    }
}