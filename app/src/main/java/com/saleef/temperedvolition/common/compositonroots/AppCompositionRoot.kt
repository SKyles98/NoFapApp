package com.saleef.temperedvolition.common.compositonroots

import com.saleef.temperedvolition.networking.ZenQuotesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Our composition root to init services that are active throughout the application lifecycle
class AppCompositionRoot {



    private val retrofit:Retrofit by lazy{
        Retrofit.Builder().baseUrl("https://zenquotes.io/api/").addConverterFactory(GsonConverterFactory.create()).build()
    }



     val zenQuotesApi:ZenQuotesApi by lazy{
        retrofit.create(ZenQuotesApi::class.java)
    }
}