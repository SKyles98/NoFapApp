package com.saleef.temperedvolition

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.saleef.temperedvolition.views.streakscreen.StreakFragment

class ScreenNavigator(private val manager: FragmentManager) {

    private val streakFragment:Fragment = StreakFragment()

    fun toStreakScreen(){
        manager.beginTransaction().replace(R.id.fragment_Container,StreakFragment()).addToBackStack(null).commit()
    }


}