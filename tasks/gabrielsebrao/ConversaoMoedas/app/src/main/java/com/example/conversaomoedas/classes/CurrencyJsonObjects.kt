package com.example.conversaomoedas.classes

import com.google.gson.annotations.SerializedName

data class CurrencyJsonObjects(

    @SerializedName("firstCurrency")
    val initialCurrency: CurrencyJsonItems,

    @SerializedName("secondCurrency")
    val finalCurrency: CurrencyJsonItems

)