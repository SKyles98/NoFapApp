package com.saleef.temperedvolition.views.streakscreen



import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import com.google.android.material.button.MaterialButton
import com.saleef.temperedvolition.MainActivity

import com.saleef.temperedvolition.R
import com.saleef.temperedvolition.common.constants.Constants
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
   private val background:ConstraintLayout
   private val relapseButton:MaterialButton
   private var flag:Int = 0
   private var initTime:Long = 0
    init {

        progressBar = findViewById(R.id.streakProgress)
        timerTxt = findViewById(R.id.currentTimeTxt)
        daysTxt = findViewById(R.id.currentDaysTxt)
        highestTxt = findViewById(R.id.bestStreakTxt)
        relapseButton = findViewById(R.id.relapseButton)

        background = findViewById(R.id.streakBackGround)
        //ToolBar init
        toolbar = findViewById(R.id.toolBar)
        toolbar.title = "Streak"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(context.resources.getColor(R.color.white, context.theme))
        } else{
            toolbar.setTitleTextColor(ContextCompat.getColor(context,R.color.white))
        }
        toolbar.inflateMenu(R.menu.settings)

        toolbar.menu.findItem(R.id.history).isVisible = false
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



    fun bindTimer(milliseconds: Long){

        if (flag==0) { // SO the first run we we can just increment from there
             initTime = milliseconds
            progressBar.progress = (initTime/60000).toInt()
            Log.i("tit",(initTime).toString())
            flag++
        } else{
            Log.i("tittie",(initTime + milliseconds).toString())
            progressBar.progress = (initTime + milliseconds/60000).toInt()
            val timeFormat =  String.format("%02d:%02d:%02d", (initTime + milliseconds) / 3600,
                ((initTime + milliseconds) % 3600) / 60, (((initTime + milliseconds) % 3600) % 60))
            Log.i("tyga",timeFormat)
            timerTxt.text = timeFormat
        }
    }

    fun resetText(){
        initTime = 0
        progressBar.progress = 0
    }


    fun bindDaysPassed(days:Int){
        daysTxt.text = days.toString()
    }


    fun bindNightMode(status:Int){
        val white: Int = ContextCompat.getColor(context,R.color.white)
        val black: Int = ContextCompat.getColor(context,R.color.black)
        if (status == Constants.NIGHTMODE){
            daysTxt.setTextColor(white)
            timerTxt.setTextColor(white)
            highestTxt.setTextColor(white)
            highestTxt.setBackgroundColor(black)
            background.setBackgroundColor(black)
            toolbar.setBackgroundColor(black)
            relapseButton.setTextColor(white)
            progressBar.setBackgroundColor(black)
        } else{
            daysTxt.setTextColor(black)
            timerTxt.setTextColor(black)
            highestTxt.setTextColor(black)
            relapseButton.setTextColor(black)
            progressBar.setBackgroundColor(white)
            background.setBackgroundColor(white)
            toolbar.setBackgroundColor(ContextCompat.getColor(context,R.color.blue))
            highestTxt.setBackgroundColor(ContextCompat.getColor(context,R.color.light_blue))


        }
    }
}