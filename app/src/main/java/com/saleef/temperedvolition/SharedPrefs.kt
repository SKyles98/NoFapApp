package com.saleef.temperedvolition

import android.content.SharedPreferences


class SharedPrefs(private val sharedPreferences: SharedPreferences) {





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

    fun setStartDate(date:String){
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(START_DATE,date)
        editor.apply()
    }

    fun getStartDate(): String? {
        return sharedPreferences.getString(START_DATE,"NONE")
    }

    companion object{
        const val START_STATUS:String = "START STATUS"
        const val CURRENT_DAYS:String = "CURR_DAYS"
        const val TIMER_STATE:String = "TIMER STATE"
        const val START_TIME:String = "START TIME"
        const val START_DATE:String = "START DATE"
    }
}