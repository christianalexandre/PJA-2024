package com.example.apimoedas

import com.google.gson.annotations.SerializedName

data class Posts (
    @SerializedName("code")
    var code: String,

    @SerializedName("codein")
    var codein: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("high")
    var high: Double,

    @SerializedName("low")
    var low: Double,

    @SerializedName("varBid")
    var varBid: Double,

    @SerializedName("pctChange")
    var pctChange: Double,

    @SerializedName("bid")
    var bid: Double,

    @SerializedName("ask")
    var ask: Double,

    @SerializedName("timestamp")
    var timesstamp: Int,

    @SerializedName("create_date")
    var create_date: String
)