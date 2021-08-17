package com.saleef.temperedvolition

import android.app.Application
import android.content.Context
import android.content.IntentFilter


class CustomApplication:Application() {


    override fun onCreate() {
        super.onCreate() // Set our database instance before the anything else is initialized
        HistoryNoteDataBase.getDataBase(this)
    }


}