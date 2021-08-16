package com.saleef.temperedvolition.views.streakscreen


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter


import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.saleef.temperedvolition.AlarmHelper
import com.saleef.temperedvolition.BaseFragment
import com.saleef.temperedvolition.SharedPrefs
import com.saleef.temperedvolition.TimerService



class StreakFragment: BaseFragment(),StreakViewImpl.Listener {

    private lateinit var streakView:StreakViewImpl
    private  val timerReceiver:TimerReciever = TimerReciever()
    private  lateinit var sharedPrefs: SharedPrefs
    private lateinit var  alarmHelper:AlarmHelper

    companion object{
        const val SECONDSKEY = "SECONDSKEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("service", "Started")
        sharedPrefs = compositionRoot.sharedPrefs
        alarmHelper = compositionRoot.alarmHelper

        val serviceIntent = Intent(context, TimerService::class.java)

            // If service is not started then we save current date
           // Save the date and then set a boolean flag that determines if need to save the again
        // We only set this flag to true if user hits the relapse button which resets the service and alarm from the beginning

        if (!sharedPrefs.getStartStatus()) {
            alarmHelper.initAlarmTimer()
            sharedPrefs.setStartStatus(true)
            serviceIntent.putExtra(SECONDSKEY,alarmHelper.twentyFourHours())
        } else{
            serviceIntent.putExtra(SECONDSKEY,  (alarmHelper.getRemainingTimerDuration()).toInt()) // Gets the time left needed to complete the goal
        }
                requireActivity().startService(serviceIntent)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
         streakView = StreakViewImpl(inflater, container)

        return streakView.rootView
    }


    override fun onStart() {
        super.onStart()
        Log.i("called", "hi")
        streakView.registerListener(this)
        requireActivity().registerReceiver(timerReceiver, IntentFilter(TimerService.ACTION_TICK))
        requireActivity().registerReceiver(timerReceiver, IntentFilter(TimerService.ACTION_FINISHED))
        bindView()
    }

//Problem Timer is ending as soon as it starts
   private fun bindView(){ // Will show the representation of our left off time
       Log.i("t",(alarmHelper.getStartingTimeOffset().toString()))

      streakView.bindTimer(alarmHelper.getStartingTimeOffset())
       streakView.bindDaysPassed(sharedPrefs.getDays())
   }

    override fun onStop() {
        super.onStop()
        streakView.unRegisterListener(this)

        try {
            requireActivity().unregisterReceiver(timerReceiver)
        } catch (e: Exception){
            // Reciever was already unregistered
        }
    }

    override fun onResetClicked() {
        // Show dialog where user can enter option to give a reason for relapse then stop and start service
        // After clicking cancel dont stop, Clicking relapse stop service and starts new one
        Log.i("hello", "Stop Please")
        requireActivity().stopService(Intent(context, TimerService::class.java))
        alarmHelper.cancelAlarmTimer()
        //requireActivity().startService(Intent(context,TimerService::class.java))
    }

    override fun onSettingsClicked() {

    }



    override fun onDestroy() {
        super.onDestroy()
        requireActivity().stopService(Intent(context, TimerService::class.java))
    }


  private inner class TimerReciever : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent == null) return

            when (intent.action) {
                TimerService.ACTION_TICK -> {
                    val timeLeft = intent.getIntExtra(TimerService.TIME_LEFT_KEY, 0)
                    Log.i("p", timeLeft.toString())
                    streakView.bindTimer(timeLeft.toLong())
                }
                TimerService.ACTION_FINISHED -> {
                    Toast.makeText(requireContext(), "ZeHahahaha", Toast.LENGTH_LONG).show()
                    streakView.resetText()
                    streakView.bindDaysPassed(sharedPrefs.getDays())
                    val serviceIntent = Intent(context, TimerService::class.java)
                    requireActivity().stopService(serviceIntent)
                    serviceIntent.putExtra(SECONDSKEY,86400)
                    requireActivity().startService(serviceIntent)
                }
            }
        }
    }

}