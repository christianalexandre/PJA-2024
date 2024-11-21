package com.example.conversaomoedas.classes

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {
    @GET("{currencies}")
    fun getCurrencies(
        @Path("currencies") currencies: String
    ): Observable<List<CurrencyJsonItems>>
}