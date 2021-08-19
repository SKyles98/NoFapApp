package com.saleef.temperedvolition.networking

import com.google.gson.annotations.SerializedName

data class QuoteSchema(@SerializedName("q")val quote:String, @SerializedName("a")val author:String)

