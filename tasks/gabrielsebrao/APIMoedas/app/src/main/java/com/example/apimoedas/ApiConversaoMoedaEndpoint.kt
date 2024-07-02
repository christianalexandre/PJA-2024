package com.example.apimoedas

import retrofit2.Call
import retrofit2.http.GET

interface ApiConversaoMoedaEndpoint {
    @GET("posts")
    fun getPosts() : Call<List<Posts>>
}