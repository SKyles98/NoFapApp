package com.saleef.temperedvolition.networking


import retrofit2.Response
import retrofit2.http.GET


interface ZenQuotesApi {

    @GET("quotes")
   suspend fun fetchQuotes():Response<List<QuoteSchema>>

   

}