package com.example.contactdefinitive

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultModel(
    val currencies: Map<String, askValue>
)

@JsonClass(generateAdapter = true)
data class askValue(
    @Json(name = "ask")
    val coinValue: String = ""
)
