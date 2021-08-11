package com.saleef.temperedvolition

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.saleef.temperedvolition.views.streakscreen.StreakFragment

class BackGroundTimerReceiver(private val sharedPrefs: SharedPrefs,private val activity:FragmentActivity):BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent==null) return

        when(intent.action){
            TimerService.ACTION_TICK -> {
                Log.i("p", "Cmon")
                val timeLeft = intent.getIntExtra(TimerService.TIME_LEFT_KEY, 0)
                sharedPrefs.saveTime(timeLeft)

            }
            TimerService.ACTION_FINISHED -> {
                val serviceIntent = Intent(context,TimerService::class.java)
                serviceIntent.putExtra(StreakFragment.SECONDSKEY,0)
                activity.startService(serviceIntent)
            }
        }
    }
}