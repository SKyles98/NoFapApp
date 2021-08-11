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

import com.saleef.temperedvolition.BaseFragment
import com.saleef.temperedvolition.SharedPrefs
import com.saleef.temperedvolition.TimerService


class StreakFragment: BaseFragment(),StreakViewImpl.Listener {

    private lateinit var streakView:StreakViewImpl
    private  val timerReceiver:TimerReciever = TimerReciever()
    private  lateinit var sharedPrefs: SharedPrefs

    companion object{
        const val SECONDSKEY = "SECONDSKEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("service", "Started")
        sharedPrefs = compositionRoot.sharedPrefs
            val serviceIntent = Intent(context,TimerService::class.java)
            serviceIntent.putExtra(SECONDSKEY,sharedPrefs.getLatestTime())
            requireActivity().startService(serviceIntent) // Starts the service


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

   private fun bindView(){
       streakView.bindTimer(sharedPrefs.getLatestTime())
       streakView.dayPassed(sharedPrefs.getDays())
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
        //requireActivity().startService(Intent(context,TimerService::class.java))
    }

    override fun onSettingsClicked() {

    }

    private fun saveTime(){

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
                    sharedPrefs.saveTime(timeLeft)

                    streakView.bindTimer(timeLeft)
                }
                TimerService.ACTION_FINISHED -> {
                    Toast.makeText(requireContext(),"RORORo",Toast.LENGTH_LONG).show()

                }
            }
        }
    }

}