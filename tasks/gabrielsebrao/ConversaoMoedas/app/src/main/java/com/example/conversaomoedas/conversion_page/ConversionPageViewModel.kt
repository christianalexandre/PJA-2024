package com.example.conversaomoedas.conversion_page

import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.conversaomoedas.classes.CurrencyApi
import com.example.conversaomoedas.classes.CurrencyEnum
import com.example.conversaomoedas.classes.CurrencyJsonObjects
import com.example.conversaomoedas.classes.RetrofitInstance
import retrofit2.Response
import java.net.UnknownHostException

class  ConversionPageViewModel: ViewModel() {

    var convertedValue: Double = 0.0
    var finalValue: Double = 0.0
    lateinit var resources: Resources

    fun convertValues(initialCurrencyCode: String, finalCurrencyCode: String) {

        convertedValue *= CurrencyEnum.entries.first { it.getCode(resources) == initialCurrencyCode }.valueToReal
        finalValue = convertedValue / CurrencyEnum.entries.first { it.getCode(resources) == finalCurrencyCode }.valueToReal

    }

    fun getAPIValue(context: Context, lifecycleOwner: LifecycleOwner, currencyCode: String) {

        val retrofitService = RetrofitInstance.getRetrofitInstance().create(CurrencyApi::class.java)

        val responseLiveData: LiveData<Response<CurrencyJsonObjects>> = liveData {

            try {
                emit(retrofitService.getCurrencies(currencyCode))
            } catch(_: UnknownHostException) {
                Toast.makeText(context, "deu errado na api", Toast.LENGTH_LONG).show()
            }

        }

        responseLiveData.observe(lifecycleOwner) {
            val apiData = it.body()
            
        }

    }

}