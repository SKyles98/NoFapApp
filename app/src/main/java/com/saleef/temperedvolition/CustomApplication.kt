package com.saleef.temperedvolition

import android.app.Application
import com.saleef.temperedvolition.DataBase.HistoryNoteDataBase
import com.saleef.temperedvolition.common.compositonroots.AppCompositionRoot


class CustomApplication:Application() {

      lateinit var appCompositionRoot: AppCompositionRoot

    override fun onCreate() {
        appCompositionRoot = AppCompositionRoot()
        super.onCreate() // Set our database instance before the anything else is initialized
        HistoryNoteDataBase.getDataBase(this)

    }


}