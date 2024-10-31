package com.example.conversaomoedas.conversion_page

import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.conversaomoedas.classes.CurrencyApi
import com.example.conversaomoedas.classes.CurrencyEnum
import com.example.conversaomoedas.classes.CurrencyJsonItems
import com.example.conversaomoedas.classes.CurrencyJsonObjects
import com.example.conversaomoedas.classes.RetrofitInstance
import com.example.conversaomoedasapi.R
import retrofit2.Response
import java.net.UnknownHostException


class ConversionPageViewModel: ViewModel() {

    private lateinit var initialCurrency: CurrencyEnum
    private lateinit var finalCurrency: CurrencyEnum

    var convertedValue: Double = 0.0
    var finalValue: Double = 0.0

    lateinit var resources: Resources

    fun currencies(initialCurrency: CurrencyEnum, finalCurrency: CurrencyEnum): ConversionPageViewModel {

        this.initialCurrency = initialCurrency
        this.finalCurrency = finalCurrency
        return this

    }

    fun resources(resources: Resources): ConversionPageViewModel {

        this.resources = resources
        return this

    }

    suspend fun convertValues() {

        initialCurrency.valueToReal = getAPIValue(initialCurrency.getCode(resources))
        finalCurrency.valueToReal = getAPIValue(finalCurrency.getCode(resources))

        Log.e("coiso", "initialCurrency: ${initialCurrency.valueToReal} finalCurrency: ${finalCurrency.valueToReal}")

        convertedValue *= initialCurrency.valueToReal
        finalValue = convertedValue / finalCurrency.valueToReal

    }

    private suspend fun getAPIValue(currencyCode: String): Double {

        if(currencyCode == resources.getString(R.string.currency_code_brazilian_real)) { return 1.0 }

        val retrofitService = RetrofitInstance.getRetrofitInstance().create(CurrencyApi::class.java)
        val response: Response<List<CurrencyJsonItems>>

        try {
            response = retrofitService.getCurrencies(currencies = currencyCode)
        } catch (e: UnknownHostException) {
            Log.e("Exceptions", "UnknownHostException")
            return 0.0
        }

        Log.e("coiso", response.toString())

        val apiData = response.body() ?: return 0.0

        return apiData[0].bid.toDoubleOrNull() ?: 0.0

    }
}