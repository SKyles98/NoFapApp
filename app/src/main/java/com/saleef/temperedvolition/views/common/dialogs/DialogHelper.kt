package com.saleef.temperedvolition.views.common.dialogs

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.saleef.temperedvolition.views.common.dialogs.RelapseDialog

class DialogHelper(private val fragmentManager: FragmentManager) {


    fun createResetDialog(){
        val d:DialogFragment = RelapseDialog()
        d.show(fragmentManager,"Relapse")
    }
}