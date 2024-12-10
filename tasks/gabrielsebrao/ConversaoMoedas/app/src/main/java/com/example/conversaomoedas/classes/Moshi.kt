package com.example.conversaomoedas.classes

import com.example.conversaomoedas.classes.currency.CurrencyJsonItems
import com.squareup.moshi.Moshi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object Moshi {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val type = Types.newParameterizedType(Map::class.java, String::class.java, CurrencyJsonItems::class.java)
    private val adapter: JsonAdapter<Map<String, CurrencyJsonItems>> = moshi.adapter(type)

    fun getStringsFromJson(json: String): Map<String, CurrencyJsonItems>? {
        return adapter.fromJson(json)
    }

}