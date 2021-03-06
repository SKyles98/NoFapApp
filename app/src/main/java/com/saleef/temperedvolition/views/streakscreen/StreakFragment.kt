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
import com.saleef.temperedvolition.*
import com.saleef.temperedvolition.DataBase.HistoryNoteDao
import com.saleef.temperedvolition.common.constants.Constants
import com.saleef.temperedvolition.views.common.base.BaseFragment
import com.saleef.temperedvolition.views.common.dialogs.DialogHelper
import com.saleef.temperedvolition.views.common.screennavigators.ScreenNavigator
import kotlinx.coroutines.*


class StreakFragment: BaseFragment(),StreakViewImpl.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private lateinit var streakView:StreakViewImpl
    private  val timerReceiver:TimerReciever = TimerReciever()
    private  lateinit var sharedPrefs: SharedPrefs
    private lateinit var  alarmHelper:AlarmHelper
    private lateinit var  historyNoteDao: HistoryNoteDao
    private lateinit var  dialogHelper: DialogHelper
    private lateinit var  screenNavigator: ScreenNavigator
    companion object{
        const val SECONDSKEY = "SECONDSKEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("service", "Started")
        sharedPrefs = compositionRoot.sharedPrefs
        alarmHelper = compositionRoot.alarmHelper
        historyNoteDao = compositionRoot.historyNoteDao
        dialogHelper = compositionRoot.dialogHelper
        screenNavigator = compositionRoot.screenNavigator
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
         streakView = compositionRoot.viewFactory.newStreakViewImpl(container)
        catchdialogResult()
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


   private fun bindView(){
       Log.i("t",(alarmHelper.getStartingTimeOffset().toString()))
       streakView.bindNightMode(sharedPrefs.getVisualPref())
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

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().stopService(Intent(context, TimerService::class.java))
    }


    override fun onResetClicked() {
        dialogHelper.createResetDialog()
    }


    override fun onSettingsClicked() {
        screenNavigator.toSettingsScreen()
    }




    private fun catchdialogResult(){
        parentFragmentManager.setFragmentResultListener(Constants.REQUESTKEY,this,{ _:String, bundle:Bundle ->
            bundle.getString(Constants.NOTEKEY)?.let { addNote(it) }
        })
    }


    private fun addNote(note:String){


        coroutineScope.launch {
            try{
                historyNoteDao.addNote(alarmHelper.getNote(note))
            } finally {
                val serviceIntent = Intent(context,TimerService::class.java)
                requireActivity().stopService(serviceIntent)
                alarmHelper.resetAlarm()
                streakView.resetText()
                serviceIntent.putExtra(SECONDSKEY,alarmHelper.twentyFourHours())
                requireActivity().startService(serviceIntent)
                Toast.makeText(requireContext(),"Added",Toast.LENGTH_LONG).show()
            }
        }

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