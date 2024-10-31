package com.example.conversaomoedas.conversion_page

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conversaomoedas.classes.CurrencyApi
import com.example.conversaomoedas.classes.CurrencyEnum
import com.example.conversaomoedas.classes.CurrencyJsonItems
import com.example.conversaomoedas.classes.RetrofitInstance
import com.example.conversaomoedasapi.R
import com.example.conversaomoedasapi.databinding.ActivityConversionPageBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.UnknownHostException


class ConversionPageViewModel: ViewModel() {

    var convertedValue: Double = 0.0
    var finalValue: Double = 0.0
    lateinit var resources: Resources

    suspend fun convertValues(context: Context, initialCurrencyCode: String, finalCurrencyCode: String) {

            val initialCurrency = CurrencyEnum.entries.first { it.getCode(resources) == initialCurrencyCode }
            val finalCurrency = CurrencyEnum.entries.first { it.getCode(resources) == finalCurrencyCode }

            initialCurrency.valueToReal = getAPIValue(context, initialCurrencyCode)
            finalCurrency.valueToReal = getAPIValue(context, finalCurrencyCode)

            Log.e("coiso", "initialCurrency: ${initialCurrency.valueToReal} finalCurrency: ${finalCurrency.valueToReal}")

            convertedValue *= initialCurrency.valueToReal
            finalValue = convertedValue / finalCurrency.valueToReal

    }

    private suspend fun getAPIValue(context: Context, currencyCode: String): Double {

        if(currencyCode == resources.getString(R.string.currency_code_brazilian_real)) { return 1.0 }

        val retrofitService = RetrofitInstance.getRetrofitInstance().create(CurrencyApi::class.java)
        val response: Response<List<CurrencyJsonItems>>

        try {
            response = retrofitService.getCurrencies(currencies = currencyCode)
        } catch (e: UnknownHostException) {
            Log.e("coiso", "Erro de conexão")
            withContext(Dispatchers.Main) { Toast.makeText(context, "Erro de conexão com a API", Toast.LENGTH_LONG).show() }
            return 0.0
        }

        Log.e("coiso", response.toString())

        val apiData = response.body() ?: return 0.0

        return apiData[0].bid.toDoubleOrNull() ?: 0.0

    }
}