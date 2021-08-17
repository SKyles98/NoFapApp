package com.saleef.temperedvolition

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.saleef.temperedvolition.views.historyscreen.HistoryFragment
import com.saleef.temperedvolition.views.streakscreen.StreakFragment

class ScreenNavigator(private val manager: FragmentManager) {



    fun toStreakScreen(){
        manager.beginTransaction().replace(R.id.fragment_Container,StreakFragment()).commit()
    }

    fun toHistoryScreen() {
        manager.beginTransaction().replace(R.id.fragment_Container,HistoryFragment()).commit()
    }


}