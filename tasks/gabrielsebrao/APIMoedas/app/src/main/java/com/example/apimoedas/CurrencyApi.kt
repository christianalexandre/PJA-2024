package com.example.apimoedas

import retrofit2.Response
import retrofit2.http.GET

interface CurrencyApi {
    @GET("USD")
    suspend fun getCurrencies(): Response<CurrencyItem>
}