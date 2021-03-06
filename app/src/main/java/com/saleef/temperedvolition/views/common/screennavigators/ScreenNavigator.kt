package com.saleef.temperedvolition.views.common.screennavigators

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.saleef.temperedvolition.R
import com.saleef.temperedvolition.views.common.settingsscreen.SettingsFragment
import com.saleef.temperedvolition.views.historyscreen.HistoryFragment
import com.saleef.temperedvolition.views.motivationscreen.MotivationFragment
import com.saleef.temperedvolition.views.streakscreen.StreakFragment

class ScreenNavigator(private val manager: FragmentManager) {



    private val streakFragment:StreakFragment = StreakFragment()
    private val historyFragment:HistoryFragment = HistoryFragment()
    private val motivationFragment:MotivationFragment = MotivationFragment()
    private var active:Fragment = streakFragment

    fun addScreens(){
        manager.beginTransaction().add(R.id.fragment_Container,historyFragment).hide(historyFragment).commit()
        manager.beginTransaction().add(R.id.fragment_Container,motivationFragment).hide(motivationFragment).commit()
        manager.beginTransaction().add(R.id.fragment_Container,streakFragment).commit()
    }

    fun setActive(currFrag:String){
        when(currFrag){
            "streak"-> {
                active = streakFragment
            }
            "history" ->{
                active = historyFragment
            }
            "motivation" ->{
                active = motivationFragment
            }
        }
    }
    fun toStreakScreen(){
        manager.beginTransaction().hide(active).show(streakFragment).commit()
    }

    fun toHistoryScreen() {
        manager.beginTransaction().hide(active).show(historyFragment).commit()
    }

    fun toMotivationScreen(){
        manager.beginTransaction().hide(active).show(motivationFragment).commit()
    }
    fun  navigateUp()
    {
        if (manager.isStateSaved) {
            return
        }

        if (manager.backStackEntryCount > 0) {

            // In a normal world, just popping back stack would be sufficient, but since android
            // is not normal, a call to popBackStack can leave the popped fragment on screen.
            // Therefore, we start with manual removal of the current fragment.
            // Description of the issue can be found here: https://stackoverflow.com/q/45278497/2463035
            removeCurrentFragment()

            if (manager.popBackStackImmediate()) {
                return
            }
        }
    }




    private fun removeCurrentFragment() {
        val ft: FragmentTransaction = manager.beginTransaction()
        getCurrentFragment()?.let { ft.remove(it) }
        ft.commitNow()
    }

    private fun getCurrentFragment(): Fragment? { // Gets the fragment that is currently in the FrameLayout
        return manager.findFragmentById(R.id.fragment_Container)
    }

    fun toSettingsScreen() { // Settings isnt apart of the the bottom navigation so we dont hide/show it
        manager.beginTransaction().replace(R.id.fragment_Container,SettingsFragment()).addToBackStack(null).commit()
    }


}