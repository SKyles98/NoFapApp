package com.saleef.temperedvolition.views.streakscreen


import android.util.Log
import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton

import com.saleef.temperedvolition.R
import com.saleef.temperedvolition.views.common.viewmvc.BaseViewMvc


class StreakViewImpl(private val layoutinflater: LayoutInflater, private val parent: ViewGroup?):BaseViewMvc<StreakViewImpl.Listener>
    (layoutinflater, parent, R.layout.streak_screen)  {

    interface Listener{
       fun onResetClicked()
       fun onSettingsClicked()
    }

   private val toolbar:Toolbar
   private val progressBar:ProgressBar
   private val timerTxt:TextView
   private val daysTxt: TextView
   private val highestTxt:TextView
   private val relapseButton:MaterialButton

    init {
         toolbar = findViewById(R.id.toolBar)
        progressBar = findViewById(R.id.streakProgress)
        timerTxt = findViewById(R.id.currentTimeTxt)
        daysTxt = findViewById(R.id.currentDaysTxt)
        highestTxt = findViewById(R.id.bestStreakTxt)
        relapseButton = findViewById(R.id.relapseButton)
        toolbar.title = "Streak"
        toolbar.inflateMenu(R.menu.settings)
        toolbar.setOnMenuItemClickListener{
            when (it.itemId){
                R.id.settings -> {
                    for (listener in listeners) {
                        listener.onSettingsClicked()
                    }
                    return@setOnMenuItemClickListener true
                }
                else -> false
            }
        }
        relapseButton.setOnClickListener { for (listener in listeners) listener.onResetClicked() }
    }

    /*
     I need global timezone time that will get loaded into timertxt
     This timer needs to continue running while application is closed and resets when we hit 24hrs
     Current days starts at 0 but gets incremented everytime 24 hours passes
     */

    // Recieve milliseconds and start the timer (User is on the app at this point)
    // We still need the timer count while app is closed

    fun bindTimer(seconds: Int){
        val timeFormat =  String.format("%02d:%02d:%02d", seconds / 3600,
            (seconds % 3600) / 60, (seconds % 60))
        timerTxt.text = timeFormat
    }


    fun dayPassed(days:Int){
        daysTxt.text = days.toString()
    }
}