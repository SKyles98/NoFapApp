package com.saleef.temperedvolition.common.compositonroots

import android.view.LayoutInflater
import com.saleef.temperedvolition.*
import com.saleef.temperedvolition.DataBase.HistoryNoteDao
import com.saleef.temperedvolition.networking.ZenQuotesApi
import com.saleef.temperedvolition.quote.FetchQuotesUseCase
import com.saleef.temperedvolition.views.common.dialogs.DialogHelper
import com.saleef.temperedvolition.views.common.screennavigators.ScreenNavigator
import com.saleef.temperedvolition.views.common.viewfactory.ViewFactory


//Initialization of services are responsible for presenting data
class PresentationCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {




    private val layoutInflater:LayoutInflater get() = activityCompositionRoot.layoutInflater

    private val zenQuotesApi:ZenQuotesApi get() = activityCompositionRoot.zenQuotesApi

    val historyNoteDao: HistoryNoteDao get() = activityCompositionRoot.historyNoteDao

    val viewFactory:ViewFactory get() = ViewFactory(layoutInflater)

    val alarmHelper:AlarmHelper get() = activityCompositionRoot.alarmHelper

    val sharedPrefs: SharedPrefs get() = SharedPrefs(activityCompositionRoot.sharedPreferences)

    val screenNavigator: ScreenNavigator get() = ScreenNavigator(activityCompositionRoot.supportFragmentManager)

    val dialogHelper: DialogHelper get() = DialogHelper(activityCompositionRoot.supportFragmentManager)

    val fetchQuotesUseCase:FetchQuotesUseCase get() = FetchQuotesUseCase(zenQuotesApi)
}