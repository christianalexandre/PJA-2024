package com.example.conversaomoedas.classes

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyApi {
    @GET("{currencies}")
    suspend fun getCurrencies(
        @Path("currencies") currencies: String
    ): Response<List<CurrencyJsonItems>>
}