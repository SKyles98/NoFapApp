package com.saleef.temperedvolition.views.common.viewfactory

import android.view.LayoutInflater
import android.view.ViewGroup
import com.saleef.temperedvolition.views.historyscreen.HistoryViewImpl
import com.saleef.temperedvolition.views.streakscreen.StreakViewImpl

// Generates our view classes
class ViewFactory(private val layoutInflater: LayoutInflater) {


    fun newHistoryViewImpl(viewGroup: ViewGroup?):HistoryViewImpl{
        return HistoryViewImpl(layoutInflater,viewGroup)
    }

    fun newStreakViewImpl(viewGroup: ViewGroup?):StreakViewImpl{
        return StreakViewImpl(layoutInflater,viewGroup)
    }

}