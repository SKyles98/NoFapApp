package com.saleef.temperedvolition

import androidx.appcompat.app.AppCompatActivity

 open class BaseActivity : AppCompatActivity() {

     /* Tied to individuals activity life cycle
     / Lazy initialization assures that its only init once and any other times this object is referenced
     / It will refer to the same object in the cache
     /
      */
        val compositionRoot by lazy{
               ActivityCompositionRoot(this,this)
       }
}