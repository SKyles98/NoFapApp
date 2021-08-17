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
       fun onVisualModeClicked()
    }

   private val toolbar:Toolbar
   private val progressBar:ProgressBar
   private val timerTxt:TextView
   private val daysTxt: TextView
   private val highestTxt:TextView
   private val relapseButton:MaterialButton
   private var flag:Int = 0
   private var initTime:Long = 0
    init {

        progressBar = findViewById(R.id.streakProgress)
        timerTxt = findViewById(R.id.currentTimeTxt)
        daysTxt = findViewById(R.id.currentDaysTxt)
        highestTxt = findViewById(R.id.bestStreakTxt)
        relapseButton = findViewById(R.id.relapseButton)

        //ToolBar init
        toolbar = findViewById(R.id.toolBar)
        toolbar.title = "Streak"
        toolbar.inflateMenu(R.menu.settings)
        toolbar.menu.findItem(R.id.history).isVisible = false
        toolbar.setOnMenuItemClickListener{
            when (it.itemId){
                R.id.visualMode -> {
                    for (listener in listeners) {
                        listener.onVisualModeClicked()
                    }
                    return@setOnMenuItemClickListener true
                }
                else -> false
            }
        }

        relapseButton.setOnClickListener { for (listener in listeners) listener.onResetClicked() }
    }



    fun bindTimer(milliseconds: Long){

        if (flag==0) { // SO the first run we we can just increment from there
             initTime = milliseconds
            val timeFormat =  String.format("%02d:%02d:%02d", initTime  / 3600,
                (initTime  % 3600) / 60, (initTime  % 60))
            Log.i("tit",(initTime).toString())
            timerTxt.text = timeFormat
            flag++
        } else{
            Log.i("tittie",(initTime + milliseconds).toString())
            val timeFormat =  String.format("%02d:%02d:%02d", (initTime + milliseconds) / 3600,
                ((initTime + milliseconds) % 3600) / 60, (((initTime + milliseconds) % 3600) % 60))
            Log.i("tyga",timeFormat)
            timerTxt.text = timeFormat
        }
    }

    fun resetText(){
        initTime = 0
    }


    fun bindDaysPassed(days:Int){
        daysTxt.text = days.toString()
    }
}