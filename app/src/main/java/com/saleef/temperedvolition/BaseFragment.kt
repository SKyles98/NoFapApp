package com.saleef.temperedvolition

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class BaseFragment :Fragment() {

    val compositionRoot:ActivityCompositionRoot by lazy{
        ((requireActivity() as BaseActivity).compositionRoot)
    }
}