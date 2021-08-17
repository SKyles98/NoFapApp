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
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saleef.temperedvolition.views.common.base.BaseActivity

// Controller that handles navigation
class MainActivity : BaseActivity() {
    private lateinit var screenNavigator: ScreenNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNav:BottomNavigationView = findViewById(R.id.bottom_Navigation)
         screenNavigator = compositionRoot.screenNavigator
        if (savedInstanceState==null){
            screenNavigator.toStreakScreen()
        }
        bottomNav.setOnItemSelectedListener {
           when (it.itemId){ // When item is clicked navigate to screen then return
                R.id.streak->{
                    //ScreenNavigator navigate to streak
                    screenNavigator.toStreakScreen()
                    return@setOnItemSelectedListener true
                }
               R.id.relapses->{
                   //Navigate to relapse Screen
                   screenNavigator.toHistoryScreen()
                   return@setOnItemSelectedListener true
               }
               R.id.motivation->{
                   //Navigate to motivation Screen
                   return@setOnItemSelectedListener true
               }
           }
            false
        }

    }


    override fun onBackPressed() {
        super.onBackPressed()
    }
}