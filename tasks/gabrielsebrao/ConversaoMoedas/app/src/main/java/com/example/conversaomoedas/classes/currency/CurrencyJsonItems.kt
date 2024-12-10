package com.example.conversaomoedas.classes.currency

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyJsonItems(
    @Json(name = "ask")
    val ask: String,

    @Json(name = "bid")
    val bid: String,

    @Json(name = "code")
    val code: String,

    @Json(name = "codein")
    val codein: String,

    @Json(name = "create_date")
    val createDate: String,

    @Json(name = "high")
    val high: String,

    @Json(name = "low")
    val low: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "pctChange")
    val pctChange: String,

    @Json(name = "timestamp")
    val timestamp: String,

    @Json(name = "varBid")
    val varBid: String
)
