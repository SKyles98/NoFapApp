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
           //TODO I need the month,day,year if this value is different next time we check it then do some equation to calcutate diff
        val init = 300
        if (!sharedPrefs.getStartStatus()) {
            alarmHelper.initAlarmTimer()
            sharedPrefs.setStartStatus(true)

            serviceIntent.putExtra(SECONDSKEY,init)
        } else{
            serviceIntent.putExtra(SECONDSKEY, (init - (alarmHelper.calculateTimeDiff())/1000).toInt()) // Gets the time left needed to complete the goal
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
        requireActivity().registerReceiver(timerReceiver,
                IntentFilter(TimerService.ACTION_FINISHED)
        )
        bindView()
    }

   private fun bindView(){ // Will show the representation of our left off time
      streakView.bindTimer(alarmHelper.calculateTimeDiff()/1000 + 50)
       streakView.bindDaysPassed(sharedPrefs.getDays())
   }

    override fun onStop() {
        super.onStop()
        streakView.unRegisterListener(this)
        alarmHelper.saveTime()
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

    }


  private inner class TimerReciever : BroadcastReceiver() {


        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent == null) return

            when (intent.action) {
                TimerService.ACTION_TICK -> {
                    Log.i("p", "Cmon")
                    val timeLeft = intent.getIntExtra(TimerService.TIME_LEFT_KEY, 0)
                    streakView.bindTimer(timeLeft.toLong())
                }
                TimerService.ACTION_FINISHED -> {
                    Toast.makeText(requireContext(), "ZeHahahaha", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}