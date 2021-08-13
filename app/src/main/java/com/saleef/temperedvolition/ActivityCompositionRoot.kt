package com.saleef.temperedvolition

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

// Intialization of services that need activity
class ActivityCompositionRoot( private val appCompatActivity: AppCompatActivity, private val fragmentActivity: FragmentActivity) {



    private val sharedPreferences:SharedPreferences by lazy {
        fragmentActivity.getSharedPreferences(
            R.string.Preferences_File_Key.toString(),
            Context.MODE_PRIVATE
        )
    }

     val alarmHelper:AlarmHelper get() = AlarmHelper(fragmentActivity,sharedPrefs)

    private val supportFragmentManager:FragmentManager get() = fragmentActivity.supportFragmentManager


     // Doesnt intialize only gets it when needed
      val sharedPrefs:SharedPrefs get() = SharedPrefs(sharedPreferences)

    val screenNavigator:ScreenNavigator get() = ScreenNavigator(supportFragmentManager)


     // Get makes sure the object is initialized only when it is called





}