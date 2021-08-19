package com.saleef.temperedvolition.views.motivationscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saleef.temperedvolition.SharedPrefs
import com.saleef.temperedvolition.quote.FetchQuotesUseCase
import com.saleef.temperedvolition.quote.Quote
import com.saleef.temperedvolition.views.common.base.BaseFragment
import kotlinx.coroutines.*
import kotlin.random.Random

class MotivationFragment : BaseFragment(),MotivationViewImpl.Listener {


    private lateinit var motivationViewImpl: MotivationViewImpl
    private lateinit var sharedPrefs: SharedPrefs


    private lateinit var fetchQuotesUseCase:FetchQuotesUseCase
    private val coroutineScope:CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = compositionRoot.sharedPrefs
        fetchQuotesUseCase = compositionRoot.fetchQuotesUseCase
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        motivationViewImpl = compositionRoot.viewFactory.newMotivationViewImpl(container)
        return motivationViewImpl.rootView
    }


    override fun onStart() {
        super.onStart()
        motivationViewImpl.registerListener(this)
        if (quoteList.isEmpty()){ // Shouldnt be empty fetch mustve failed or something
            fetchQuotes()
        } else{
            bindView()
        }
    }


    override fun onStop() {
        super.onStop()
        motivationViewImpl.unRegisterListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchQuotes(){
        coroutineScope.launch {
            motivationViewImpl.showProgressIndication() // Show loading before the fetch begins
            try{
                val result = fetchQuotesUseCase.fetchQuotes()
                when(result){// If result is Success do x
                   is FetchQuotesUseCase.Result.Success ->{
                        quoteList = result.quotes
                        bindView()
                    }
                    is FetchQuotesUseCase.Result.Failure ->{
                        onFetchFailed()
                    }
                }
            } finally {
                motivationViewImpl.hideProgressIndication()
            }
        }
    }
    private fun bindView(){
        motivationViewImpl.bindDayNight(sharedPrefs.getVisualPref())
        motivationViewImpl.bindQuote(quoteList[randNumber()])
    }



    private fun randNumber():Int{// Used to return a random quote
        return (0..49).random()
    }

    private fun onFetchFailed(){
        motivationViewImpl.bindErrorMessage()
    }
   companion object{
       private var quoteList:List<Quote> = ArrayList(0)
   }

    override fun onRefreshClicked() {
        fetchQuotes()
    }

    override fun onGimmeClicked() {
        motivationViewImpl.bindQuote(quoteList[randNumber()])
    }
}