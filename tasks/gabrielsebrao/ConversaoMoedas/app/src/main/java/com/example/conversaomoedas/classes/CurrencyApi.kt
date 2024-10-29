package com.example.conversaomoedas.classes

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {
    @GET("{symbols}")
    suspend fun getCurrencies(
        @Path("symbols") symbols: String
    ): Response<CurrencyJsonObjects>
}