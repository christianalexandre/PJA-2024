package com.example.conversaomoedas.conversion_page

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.conversaomoedas.classes.currency.CurrencyApi
import com.example.conversaomoedas.classes.currency.CurrencyEnum
import com.example.conversaomoedas.classes.currency.CurrencyJsonItems
import com.example.conversaomoedas.classes.Moshi
import com.example.conversaomoedas.classes.RetrofitInstance
import com.example.conversaomoedasapi.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody


class ConversionPageViewModel: ViewModel() {

    private lateinit var initialCurrency: CurrencyEnum
    private lateinit var finalCurrency: CurrencyEnum

    var convertedValue: Double = 0.0
    var finalValue: Double = 0.0
    var isLoading = MutableLiveData(true)
    var hasError = MutableLiveData(false)
    var conversionSuccess = MutableLiveData(false)
    private var disposable: Disposable? = null

    lateinit var resources: Resources

    fun currencies(initialCurrency: CurrencyEnum, finalCurrency: CurrencyEnum): ConversionPageViewModel = apply {
        this.initialCurrency = initialCurrency
        this.finalCurrency = finalCurrency
    }

    fun resources(resources: Resources): ConversionPageViewModel = apply {
        this.resources = resources
    }

    fun convertValues(): Disposable? {

        disposable = getApiSingle(initialCurrency.getCode(resources), finalCurrency.getCode(resources))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ responseMap ->
                val listResponse = responseMap.values.toList()

                initialCurrency.valueToReal =
                    if(initialCurrency.getName(resources) == resources.getString(R.string.currency_brazilian_real))
                        1.0
                    else
                        listResponse[0].bid.toDoubleOrNull() ?: 0.0

                finalCurrency.valueToReal =
                    if(finalCurrency.getName(resources) == resources.getString(R.string.currency_brazilian_real))
                        1.0
                    else if(initialCurrency.getName(resources) == resources.getString(R.string.currency_brazilian_real))
                        listResponse[0].bid.toDoubleOrNull() ?: 0.0
                    else
                        listResponse[1].bid.toDoubleOrNull() ?: 0.0

                isLoading.postValue(false)
                conversionSuccess.postValue(true)

                Log.e("RX_DEBUG_CONVERSION (ON SUCCESS)", "disposable is disposed: ${disposable?.isDisposed}, disposable location: ${System.identityHashCode(disposable)}")
            }, { error ->

                isLoading.postValue(false)
                hasError.postValue(true)

                Log.e("RX_DEBUG_CONVERSION (ON ERROR)", "Error during conversion: $error")
            })

        return disposable

    }

    fun onSuccess() {

        convertedValue *= initialCurrency.valueToReal
        finalValue = convertedValue / finalCurrency.valueToReal
        disposable?.dispose()

    }

    private fun getApiSingle(firstCurrencyCode: String, finalCurrencyCode: String): Single<Map<String, CurrencyJsonItems>> {

        var endpoint = ""

        if (firstCurrencyCode != resources.getString(R.string.currency_code_brazilian_real))
            endpoint += "$firstCurrencyCode,"

        if(finalCurrencyCode != resources.getString(R.string.currency_code_brazilian_real) )
            endpoint += finalCurrencyCode
        else
            endpoint = endpoint.removeSuffix(",")

        Log.d("RX_ENDPOINT", "endpoint: $endpoint")

        return RetrofitInstance.getRetrofitInstance()
            .create(CurrencyApi::class.java)
            .getCurrencies(currencies = endpoint)
            .map { responseBody ->
                val jsonString = responseBody.string()
                Log.e("RX_DEBUG_CONVERSION_RESPONSE", jsonString)
                Moshi.getMapFromJson(jsonString) ?: emptyMap()
            }
    }
}