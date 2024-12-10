package com.example.contactdefinitive

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultModel(

    @Json(name = "BRLUSD")
    val brlusd: Brlusd
)

@JsonClass(generateAdapter = true)
data class Brlusd(
    @Json(name = "ask")
    val coinValue: String = ""
)
