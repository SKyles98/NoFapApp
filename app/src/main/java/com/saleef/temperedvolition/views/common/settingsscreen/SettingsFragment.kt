package com.saleef.temperedvolition.views.common.settingsscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.saleef.temperedvolition.MainActivity
import com.saleef.temperedvolition.views.common.screennavigators.ScreenNavigator
import com.saleef.temperedvolition.SharedPrefs
import com.saleef.temperedvolition.views.common.base.BaseFragment

class SettingsFragment:BaseFragment(),SettingsViewImpl.Listener,MainActivity.OnDayNightStateChanged {


    private lateinit var sharedPrefs: SharedPrefs
    private lateinit var settingsViewImpl: SettingsViewImpl
    private lateinit var screenNavigator: ScreenNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = compositionRoot.sharedPrefs
        screenNavigator = compositionRoot.screenNavigator
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
    }

    override fun onStop() {
        super.onStop()
        settingsViewImpl.unRegisterListener(this)
    }

    private fun bindSwitch(){
        settingsViewImpl.bindDayNightMode(sharedPrefs.getVisualPref())
    }
    override fun onSwitchCLicked(checked: Boolean) {
        if (checked){
           AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            sharedPrefs.saveVisualPref(2) // save nightmode

        } else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharedPrefs.saveVisualPref(1) // save daymode
        }
    }

    override fun onNavigationClicked() {
        screenNavigator.navigateUp()
    }

    override fun onDayNightApplied(state: Int) {
        settingsViewImpl.bindDayNightMode(state)
    }


}