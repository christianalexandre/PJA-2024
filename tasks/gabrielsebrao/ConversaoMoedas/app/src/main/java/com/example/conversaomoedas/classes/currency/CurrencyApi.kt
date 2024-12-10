package com.example.conversaomoedas.classes.currency

import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {
    @GET("{currencies}")
    fun getCurrencies(
        @Path("currencies") currencies: String
    ): Single<ResponseBody>
}