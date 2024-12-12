package com.example.contactdefinitive.network

import com.example.contactdefinitive.ResultModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoint {
    @GET("last/{coinType1}-{coinType2}")
    fun getCurrencyRate(
        @Path("coinType1") coinType1 : String,
        @Path("coinType2") coinType2 : String
    ): Single<ResultModel>

    //    @GET("/gh/fawazahmed0/currency-api@1/latest/currencies.json")
//    fun getCurriences() : Call<JsonObject>
}