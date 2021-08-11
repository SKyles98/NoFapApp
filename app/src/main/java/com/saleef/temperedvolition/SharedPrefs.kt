package com.saleef.temperedvolition

import android.content.SharedPreferences
import android.util.Log

class SharedPrefs(private val sharedPreferences: SharedPreferences) {


    //Store the highest day
    //Store the day for the current streak
    //Store the seconds(happens when user clicks on app

    // Method that recieves a one or a zero to notify that a service has been started
    // If 1 then that service has already been started so we dont start it again in fragment onCreate
    fun serviceNotify(started:Int){
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(START_STATUS,started)
        editor.apply()
    }

    fun getStartStatus():Boolean{
       return sharedPreferences.getInt(START_STATUS,0) == 0
    }

    fun saveTime(time: Int){
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(CURRENT_TIME,time)
        Log.i("saved?",time.toString())
        editor.apply()
    }

    fun getLatestTime():Int{
        return sharedPreferences.getInt(CURRENT_TIME,0)
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


    companion object{
        const val START_STATUS:String = "START STATUS"
        const val CURRENT_TIME:String = "CURRENT TIME"
        const val CURRENT_DAYS:String = "CURR_DAYS"
    }
}