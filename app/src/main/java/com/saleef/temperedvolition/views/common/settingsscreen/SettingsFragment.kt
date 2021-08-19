package com.saleef.temperedvolition.views.common.settingsscreen

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saleef.temperedvolition.MainActivity
import com.saleef.temperedvolition.R
import com.saleef.temperedvolition.views.common.screennavigators.ScreenNavigator
import com.saleef.temperedvolition.SharedPrefs
import com.saleef.temperedvolition.views.common.base.BaseFragment
import java.lang.ClassCastException

class SettingsFragment:BaseFragment(),SettingsViewImpl.Listener{



   interface Listener{
        fun hideBottomBar()
        fun showBottomBar()
    }
    private lateinit var listener:Listener
    private lateinit var sharedPrefs: SharedPrefs
    private lateinit var settingsViewImpl: SettingsViewImpl
    private lateinit var screenNavigator: ScreenNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = compositionRoot.sharedPrefs
        screenNavigator = compositionRoot.screenNavigator
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener){
            listener = context
        } else{
            throw ClassCastException(context.toString() + "Must Implement the interface")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewImpl = compositionRoot.viewFactory.newSettingsViewImpl(container)
        return settingsViewImpl.rootView
    }

    override fun onStart() {
        super.onStart()
        settingsViewImpl.registerListener(this)
        bindSwitch()
        hideNavBar()
    }

    override fun onStop() {
        super.onStop()
        settingsViewImpl.unRegisterListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        listener.showBottomBar()
    }
    private fun bindSwitch(){
        settingsViewImpl.bindDayNightMode(sharedPrefs.getVisualPref())
    }

    private fun hideNavBar(){
      listener.hideBottomBar()
    }

    override fun onSwitchCLicked(checked: Boolean) {
        if (checked){
           AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            sharedPrefs.saveVisualPref(2) // save nightmode
            settingsViewImpl.bindDayNightMode(sharedPrefs.getVisualPref())
        } else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharedPrefs.saveVisualPref(1) // save daymode
        }
    }

    override fun onNavigationClicked() {
        screenNavigator.navigateUp()
    }




}