package com.saleef.temperedvolition
/*
THis will be my first semi-serious application to become familiar with kotlin and its features.
Additionally, I will use this app as a way to improve knowledge on DI and other kotlin libraries
 */

/*
Features of this app will include a timer that keeps track of progress.
History of all attempts with a note feature
Reward system that grants titles and background pics based off success
notifications send to user when they hit a milestone
Resource section for verified sources
Maybe a todo list
 */
/*

TODO fix fragment duplication when user rapidly clicks on a bottomnav option
TODO refractor pure dependency injection to hilt library (need to learn)

TODO implement highest Days for the ui
 */
import android.os.Build


import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saleef.temperedvolition.common.constants.Constants
import com.saleef.temperedvolition.views.common.base.BaseActivity
import com.saleef.temperedvolition.views.common.screennavigators.ScreenNavigator
import com.saleef.temperedvolition.views.common.settingsscreen.SettingsFragment

// Controller that handles navigation
class MainActivity : BaseActivity(),SettingsFragment.Listener {

    private lateinit var screenNavigator: ScreenNavigator
    private lateinit var sharedPrefs: SharedPrefs
    private lateinit var bottomNav:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPrefs = compositionRoot.sharedPrefs
        screenNavigator = compositionRoot.screenNavigator
        bottomNav = findViewById(R.id.bottom_Navigation)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (sharedPrefs.getVisualPref()== Constants.NIGHTMODE){
                bottomNav.setBackgroundColor(ContextCompat.getColor(this, R.color.light_purple))
                //window.navigationBarColor = ContextCompat.getColor(this, R.color.light_purple)
            }else{
                bottomNav.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
                //window.navigationBarColor = ContextCompat.getColor(this, R.color.blue)
            }

        }




        if (savedInstanceState==null){
            screenNavigator.addScreens()
        }

        bottomNav.setOnItemSelectedListener {
           when (it.itemId){ // When item is clicked navigate to screen then return
                R.id.streak->{
                    //ScreenNavigator navigate to streak
                    screenNavigator.toStreakScreen()
                    screenNavigator.setActive("streak")
                    return@setOnItemSelectedListener true
                }
               R.id.relapses->{
                   //Navigate to relapse Screen

                   screenNavigator.toHistoryScreen()
                   screenNavigator.setActive("history")
                   return@setOnItemSelectedListener true
               }
               R.id.motivation->{
                   //Navigate to motivation Screen
                   screenNavigator.toMotivationScreen()
                   screenNavigator.setActive("motivation")
                   return@setOnItemSelectedListener true
               }
           }
            false
        }

    }






    override fun hideBottomBar() {
        bottomNav.visibility = View.GONE
    }

    override fun showBottomBar() {
        bottomNav.visibility = View.VISIBLE
    }
}