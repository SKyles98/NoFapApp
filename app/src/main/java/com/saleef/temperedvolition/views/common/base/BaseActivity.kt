package com.saleef.temperedvolition.views.common.base

import androidx.appcompat.app.AppCompatActivity
import com.saleef.temperedvolition.common.compositonroots.ActivityCompositionRoot
import com.saleef.temperedvolition.common.compositonroots.PresentationCompositionRoot

open class BaseActivity : AppCompatActivity() {

     /* Tied to individuals activity life cycle
     / Lazy initialization assures that its only init once and any other times this object is referenced
     / It will refer to the same object in the cache
     /
      */
         val activitycompositionRoot by lazy{
               ActivityCompositionRoot(this,this)
       }

      protected val compositionRoot by lazy {
           PresentationCompositionRoot(activitycompositionRoot)
       }
}