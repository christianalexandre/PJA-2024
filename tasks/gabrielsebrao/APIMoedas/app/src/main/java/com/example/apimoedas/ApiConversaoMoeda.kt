package com.example.apimoedas

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConversaoMoeda {
    companion object {
        fun getInstance(path: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}