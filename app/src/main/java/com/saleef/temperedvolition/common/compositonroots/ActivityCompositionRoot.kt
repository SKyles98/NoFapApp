package com.saleef.temperedvolition.common.compositonroots

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.saleef.temperedvolition.*
import com.saleef.temperedvolition.DataBase.HistoryNoteDao
import com.saleef.temperedvolition.DataBase.HistoryNoteDataBase
import com.saleef.temperedvolition.networking.ZenQuotesApi

// Intialization of services that need activity
class ActivityCompositionRoot( private val appCompatActivity: AppCompatActivity,
                               private val fragmentActivity: FragmentActivity,
                               private val appCompositionRoot: AppCompositionRoot) {



     val sharedPreferences:SharedPreferences by lazy {
        fragmentActivity.getSharedPreferences(
            R.string.Preferences_File_Key.toString(),
            Context.MODE_PRIVATE
        )
    }

     val zenQuotesApi:ZenQuotesApi = appCompositionRoot.zenQuotesApi

    val layoutInflater:LayoutInflater get() = LayoutInflater.from(fragmentActivity)

     val alarmHelper: AlarmHelper get() = AlarmHelper(fragmentActivity,SharedPrefs(sharedPreferences)
     )

    // Gets our Dao HistoryNoteDataBase instance ( At this point the database is already init with the application instance
     val historyNoteDao: HistoryNoteDao get() = HistoryNoteDataBase.getDataBase(fragmentActivity).getHistoryNoteDao()

     val supportFragmentManager:FragmentManager get() = fragmentActivity.supportFragmentManager





     // Get makes sure the object is initialized only when it is called





}