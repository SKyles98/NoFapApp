package com.saleef.temperedvolition

import android.app.Service
import android.content.Context

import android.content.Intent
import android.os.Bundle

import android.os.IBinder

import android.util.Log


// Handles timer in Background Service
class TimerService() : Service() {

    private lateinit var timer:CountUpTimer
  //((System.currentTimeMillis() + 86400000)/1000).toInt()
    private val finishedIntent = Intent(ACTION_FINISHED)
    private val tickIntent = Intent(ACTION_TICK)
    private lateinit var sharedPrefs:SharedPrefs


    override fun onBind(intent: Intent?): IBinder? = null



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Worked","Please????")
        val bundle:Bundle? = intent?.extras
        sharedPrefs = SharedPrefs(getSharedPreferences(R.string.Preferences_File_Key.toString(), Context.MODE_PRIVATE))
        val time: Int? = bundle?.getInt("SECONDSKEY")
        timer = if (time == 0){
            createTimer(30)
        } else{
            time?.let { createTimer(it) }!!
        }


        timer.start()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
    private fun createTimer(hourTime:Int) : CountUpTimer{
       // Hour time gets the current
        return object:CountUpTimer(hourTime,1){
            override fun onCount(count: Int) {
                Log.i("Works","Pleasu Desu")
                  tickIntent.putExtra(TIME_LEFT_KEY,count)
                sendBroadcast(tickIntent)
            }

            override fun onFinish() {
                sendBroadcast(finishedIntent)
                sharedPrefs.incrementDay(1)
                //send a 1 to the sharedPrefs method to increment the day
                resetTimer()
                //stopSelf()
            }

        }
    }
    fun resetTimer(){
        timer.cancel()
        timer = createTimer(30)
        timer.start()
    }

    companion object {
        const val ACTION_FINISHED: String = "com.saleef.temperedvolition.ACTION_FINISHED"

        const val ACTION_TICK: String = "com.saleef.temperedvolition.ACTION_TICK"

        const val TIME_LEFT_KEY: String = "timeLeft"
    }
}