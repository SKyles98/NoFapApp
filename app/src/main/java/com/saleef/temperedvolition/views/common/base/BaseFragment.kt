package com.saleef.temperedvolition.views.common.base

import androidx.fragment.app.Fragment
import com.saleef.temperedvolition.common.compositonroots.PresentationCompositionRoot
import com.saleef.temperedvolition.views.common.base.BaseActivity

open class BaseFragment :Fragment() {

   protected val compositionRoot: PresentationCompositionRoot by lazy{
        PresentationCompositionRoot(((requireActivity() as BaseActivity).activitycompositionRoot))
    }



}