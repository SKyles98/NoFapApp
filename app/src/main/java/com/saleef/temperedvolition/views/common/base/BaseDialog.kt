package com.saleef.temperedvolition.views.common.base

import androidx.fragment.app.DialogFragment
import com.saleef.temperedvolition.common.compositonroots.PresentationCompositionRoot

open class BaseDialog : DialogFragment() {


    protected val compositionRoot:PresentationCompositionRoot by lazy{
        PresentationCompositionRoot(((requireActivity() as BaseActivity).activitycompositionRoot))
    }
}