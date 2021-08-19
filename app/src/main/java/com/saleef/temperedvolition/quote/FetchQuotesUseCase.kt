package com.saleef.temperedvolition.quote

import android.util.Log
import com.saleef.temperedvolition.networking.QuoteSchema
import com.saleef.temperedvolition.networking.ZenQuotesApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class FetchQuotesUseCase(private val zenQuotesApi: ZenQuotesApi) {

//TODO If user doesnt have any internet then show a button to re get quotes then make the button invisible if the our list size is greater than 0


   sealed class Result{
       data class Success(val quotes:List<Quote>):Result()
       object Failure:Result()
   }


   suspend fun fetchQuotes():Result{
         return withContext(Dispatchers.IO) {
             try{
             val response:Response<List<QuoteSchema>>  = zenQuotesApi.fetchQuotes()
             if (response.isSuccessful && response.body()!=null){
                val schemas:List<QuoteSchema> = response.body()!!
                 val quoteArray:ArrayList<Quote> = ArrayList(50)
                 for (quoteschema in schemas){
                     Log.i("quote",quoteschema.quote)
                     quoteArray.add(Quote(quoteschema.quote,quoteschema.author))
                 }
                 return@withContext Result.Success(quoteArray)
             } else{
                 return@withContext Result.Failure
             }
         } catch (t:Throwable){
                 if (t !is CancellationException) {
                     return@withContext Result.Failure
                 } else {
                     throw t
                 }
             }
         }
    }
}