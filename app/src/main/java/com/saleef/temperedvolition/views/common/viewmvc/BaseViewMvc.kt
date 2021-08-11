package com.saleef.temperedvolition.views.common.viewmvc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

// Acts as our base class for our mvc views
// Invokes observerPattern to allow for easier communication between controllers and views
// Takes a layout inflater and ViewGroup and layoutId from a subclass and instantiates,and inflates a rootView based off those parameters
open class BaseViewMvc<Listener_Type>(private val layoutInflater: LayoutInflater,private val parent:ViewGroup?,@LayoutRes layout:Int) {

    val rootView: View = layoutInflater.inflate(layout,parent,false)
    // By Using get we make sure the object is instantiated with a new instance every time and cannot be set from the outside
    protected val context: Context get() = rootView.context

    protected val listeners  = HashSet<Listener_Type>()


     fun registerListener(listener:Listener_Type){
            listeners.add(listener)
    }

    fun unRegisterListener(listener:Listener_Type){
        listeners.remove(listener)
    }

    protected fun  <T : View>  findViewById(id:Int): T{
        return rootView.findViewById<T>(id)
    }

}