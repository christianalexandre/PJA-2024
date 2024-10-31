package com.example.conversaomoedas.classes

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val mainURL = "https://economia.awesomeapi.com.br/"

    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(mainURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}