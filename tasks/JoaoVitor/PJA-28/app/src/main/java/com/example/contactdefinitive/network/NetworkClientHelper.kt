package com.example.contactdefinitive.network

import okhttp3.OkHttpClient

object NetworkClientHelper {

    val apiClient: Endpoint by lazy {
        with(NetworkUtils) {
            OkHttpClient.Builder()
                .buildRetrofit("https://economia.awesomeapi.com.br/")
                .create(Endpoint::class.java)
        }
    }
}