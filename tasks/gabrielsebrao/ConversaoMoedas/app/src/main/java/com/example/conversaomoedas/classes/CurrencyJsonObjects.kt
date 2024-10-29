package com.example.conversaomoedas.classes

import com.google.gson.annotations.SerializedName

data class CurrencyJsonObjects(

    @SerializedName("USDBRL")
    val usd: CurrencyJsonItems,

    @SerializedName("CNYBRL")
    val cny: CurrencyJsonItems,

    @SerializedName("GBPBRL")
    val gbp: CurrencyJsonItems,

    @SerializedName("EURBRL")
    val eur: CurrencyJsonItems,

    @SerializedName("CHFBRL")
    val chf: CurrencyJsonItems,

    @SerializedName("JPYBRL")
    val jpy: CurrencyJsonItems,

    @SerializedName("CADBRL")
    val cad: CurrencyJsonItems,

    @SerializedName("BTCBRL")
    val btc: CurrencyJsonItems,

    @SerializedName("DOGEBRL")
    val doge: CurrencyJsonItems
)