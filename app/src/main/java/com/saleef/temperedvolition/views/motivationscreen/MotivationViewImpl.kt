package com.saleef.temperedvolition.views.motivationscreen


import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.saleef.temperedvolition.quote.Quote
import com.saleef.temperedvolition.R
import com.saleef.temperedvolition.common.constants.Constants
import com.saleef.temperedvolition.views.common.viewmvc.BaseViewMvc

class MotivationViewImpl(private val layoutInflater: LayoutInflater,private val viewGroup: ViewGroup?)
    : BaseViewMvc<MotivationViewImpl.Listener>(layoutInflater,viewGroup, R.layout.motivation_screen) {


    interface Listener{
        fun onRefreshClicked()
        fun onGimmeClicked()
    }
     private val progressBar:ProgressBar
     private val background:ConstraintLayout
     private val quote: TextView
     private val author:TextView
     private val refresh:MaterialButton
     private val gimme:MaterialButton
     private val toolbar:Toolbar

     init {
         progressBar = findViewById(R.id.progress)
         background = findViewById(R.id.motivationBackGround)
         quote = findViewById(R.id.quoteTxt)
         author = findViewById(R.id.authorTxt)
         refresh = findViewById(R.id.refreshBtn)
         gimme = findViewById(R.id.quoteBtn)
         toolbar = findViewById(R.id.toolBar)
         toolbar.title = "Motivation"
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             toolbar.setTitleTextColor(context.resources.getColor(R.color.white, context.theme))
         } else{
             toolbar.setTitleTextColor(ContextCompat.getColor(context,R.color.white))
         }
         gimme.setOnClickListener{for (listener in listeners)  listener.onGimmeClicked()}

         refresh.setOnClickListener{for (listener in listeners) listener.onRefreshClicked()}
     }




    fun bindQuote(passage:Quote){
        gimme.visibility = View.VISIBLE
        refresh.visibility = View.GONE
        quote.text = passage.quote
        author.text = passage.author
    }


    fun bindDayNight(state:Int){
        val white: Int = ContextCompat.getColor(context,R.color.white)
        val black: Int = ContextCompat.getColor(context,R.color.colorPrimaryDarkNight)
        val purple: Int = ContextCompat.getColor(context,R.color.light_purple)
        val blue:Int = ContextCompat.getColor(context,R.color.blue)
        val red:Int = ContextCompat.getColor(context,R.color.error_red)
        if (state== Constants.NIGHTMODE){
            quote.setTextColor(white)
            author.setTextColor(white)
            toolbar.setBackgroundColor(black)
            background.setBackgroundColor(black)
            refresh.setTextColor(white)
            refresh.setBackgroundColor(purple)
            gimme.setTextColor(white)
            gimme.setBackgroundColor(purple)
        } else{
            quote.setTextColor(black)
            author.setTextColor(black)
            toolbar.setBackgroundColor(blue)
            background.setBackgroundColor(white)
            refresh.setTextColor(black)
            refresh.setBackgroundColor(red)
            gimme.setTextColor(black)
            gimme.setBackgroundColor(blue)
        }
    }

    fun bindErrorMessage() { // Hide gimme button since we have no internet
        gimme.visibility = View.GONE
        refresh.visibility = View.VISIBLE
        quote.text = "Failure to load quote"
        author.text = "Retry?"
    }


   fun showProgressIndication(){
       progressBar.visibility = View.VISIBLE
   }

    fun hideProgressIndication(){
        progressBar.visibility = View.GONE
    }
}