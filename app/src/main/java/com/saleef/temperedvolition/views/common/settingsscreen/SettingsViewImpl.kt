package com.saleef.temperedvolition.views.common.settingsscreen

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

import com.google.android.material.switchmaterial.SwitchMaterial
import com.saleef.temperedvolition.MainActivity
import com.saleef.temperedvolition.R
import com.saleef.temperedvolition.views.common.viewmvc.BaseViewMvc

class SettingsViewImpl(private val layoutInflater: LayoutInflater,private val viewGroup: ViewGroup?) :
BaseViewMvc<SettingsViewImpl.Listener>(layoutInflater,viewGroup, R.layout.settings_screen){


    interface Listener{
      fun onSwitchCLicked(checked:Boolean)
      fun onNavigationClicked()
    }

    private val switch:SwitchMaterial
    private val toolbar:Toolbar
    private val textView:TextView
    private val constraintLayout:ConstraintLayout
    init{
        constraintLayout = findViewById(R.id.settingsBackground)
        switch = findViewById(R.id.visualSwitch)
        textView = findViewById(R.id.dayNightTxt)
        toolbar = findViewById(R.id.toolBar)
        toolbar.title = "Settings"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(context.resources.getColor(R.color.white, context.theme))
        } else{
            toolbar.setTitleTextColor(ContextCompat.getColor(context,R.color.white))
        }
        toolbar.setNavigationIcon(R.drawable.ic_nav)
        toolbar.setNavigationOnClickListener{for (listener in listeners) listener.onNavigationClicked()}
        switch.setOnCheckedChangeListener{ _,ischecked:Boolean -> for (listener in listeners) listener.onSwitchCLicked(ischecked)}
    }



    fun bindDayNightMode(state:Int){
        if (state == MainActivity.OnDayNightStateChanged.NIGHT){ // NightModeConfig
            toolbar.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimaryDarkNight))
            textView.setTextColor(ContextCompat.getColor(context,R.color.colorTextNight))
            constraintLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimaryDarkNight))
            switch.isChecked = true
        } else{ // NightModeConfig
            toolbar.setBackgroundColor(ContextCompat.getColor(context,R.color.blue))
            textView.setTextColor(ContextCompat.getColor(context,R.color.black))
            constraintLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.white))
            switch.isChecked = false
        }
    }
}