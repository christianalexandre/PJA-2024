package com.example.conversaomoedas.conversion_page

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.conversaomoedas.classes.CurrencyApi
import com.example.conversaomoedas.classes.CurrencyEnum
import com.example.conversaomoedas.classes.CurrencyJsonItems
import com.example.conversaomoedas.classes.RetrofitInstance
import com.example.conversaomoedasapi.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class ConversionPageViewModel: ViewModel() {

    private lateinit var initialCurrency: CurrencyEnum
    private lateinit var finalCurrency: CurrencyEnum

    var convertedValue: Double = 0.0
    var finalValue: Double = 0.0
    var isLoading = MutableLiveData(true)
    var hasError = MutableLiveData(false)
    var conversionSuccess = MutableLiveData(false)
    var disposable: Disposable? = null

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

    fun convertValues(): Disposable? {

        val initialCurrencyObservable = getApiValue(initialCurrency.getCode(resources))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        val finalCurrencyObservable = getApiValue(finalCurrency.getCode(resources))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        disposable = Observable.zip(initialCurrencyObservable, finalCurrencyObservable) { initialValue, finalValue ->

            initialCurrency.valueToReal = if(initialValue.isEmpty()) 1.0 else initialValue[0].bid.toDoubleOrNull() ?: 0.0
            finalCurrency.valueToReal = if(finalValue.isEmpty()) 1.0 else finalValue[0].bid.toDoubleOrNull() ?: 0.0

        }.subscribe({
        }, { error ->
            Log.e("RX_DEBUG", "Error during conversion: ${error.message}")
            isLoading.postValue(false)
            hasError.postValue(true)
            disposable?.dispose()
        }, {
            Log.e(
                "RX_DEBUG",
                "initialValueToReal = ${this.initialCurrency.valueToReal} finalValueToReal = ${this.finalCurrency.valueToReal}"
            )

            convertedValue *= initialCurrency.valueToReal
            finalValue = convertedValue / finalCurrency.valueToReal

            isLoading.postValue(false)
            conversionSuccess.postValue(true)
            disposable?.dispose()
        })

        Log.e("coiso", "initialCurrency.valueToReal = ${initialCurrency.valueToReal}")

        return disposable

    }

    private fun getApiValue(currencyCode: String): Observable<List<CurrencyJsonItems>> {

        if (currencyCode == resources.getString(R.string.currency_code_brazilian_real)) return Observable.just(
            listOf()
        )

        val retrofitService = RetrofitInstance.getRetrofitInstance().create(CurrencyApi::class.java)
        val response = retrofitService.getCurrencies(currencies = currencyCode)

        return response

    }

}