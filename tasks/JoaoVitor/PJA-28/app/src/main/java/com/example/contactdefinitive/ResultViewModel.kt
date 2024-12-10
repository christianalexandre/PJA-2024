package com.example.contactdefinitive

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contactdefinitive.network.NetworkClientHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ResultViewModel : ViewModel() {

    private var disposable: Disposable? = null

    private val _resultLiveData: MutableLiveData<Float> = MutableLiveData()
    val resultLiveData: LiveData<Float> = _resultLiveData

    private val _resultErrorLiveData: MutableLiveData<String> = MutableLiveData()
    val resultErrorLiveData: LiveData<String> = _resultErrorLiveData

    fun getResult(coinType1: String, coinType2: String, value: Float) {
        disposable?.dispose()
        disposable = NetworkClientHelper.apiClient.getCurrencyRate(coinType1, coinType2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resultModel ->
                disposable?.dispose()
                _resultLiveData.value = value * resultModel.brlusd.coinValue.toFloat()
            }, {
                disposable?.dispose()
                println("error = ${it.message}")
                _resultErrorLiveData.value = it.message
            })
    }

}















































//    fun calculatingResult(coinType1: String?, coinType2: String?, value: Float?): Float {
//        var result: Float = 0f
//
//        value?.let {
//            val exchangeRates = mapOf(
//                "BRL" to "USD" to 0.18f,
//                "BRL" to "GBP" to 0.14f,
//                "BRL" to "CHF" to 0.16f,
//                "BRL" to "EUR" to 0.17f,
//                "BRL" to "KWD" to 0.053f,
//                "BRL" to "BHD" to 0.065f,
//                "BRL" to "OMR" to 0.067f,
//                "BRL" to "JOD" to 0.12f,
//                "BRL" to "KYD" to 0.14f,
//
//
//                "USD" to "BRL" to 5.42f,
//                "USD" to "GBP" to 0.75f,
//                "USD" to "CHF" to 0.85f,
//                "USD" to "EUR" to 0.90f,
//                "USD" to "KWD" to 0.31f,
//                "USD" to "BHD" to 0.38f,
//                "USD" to "OMR" to 0.39f,
//                "USD" to "JOD" to 0.71f,
//                "USD" to "KYD" to 0.83f,
//
//                "GBP" to "BRL" to 7.20f,
//                "GBP" to "USD" to 1.33f,
//                "GBP" to "CHF" to 1.13f,
//                "GBP" to "EUR" to 1.19f,
//                "GBP" to "KWD" to 0.40f,
//                "GBP" to "BHD" to 0.49f,
//                "GBP" to "OMR" to 0.50f,
//                "GBP" to "JOD" to 0.92f,
//                "GBP" to "KYD" to 1.08f,
//
//                "CHF" to "BRL" to 6.38f,
//                "CHF" to "USD" to 1.18f,
//                "CHF" to "GBP" to 0.89f,
//                "CHF" to "EUR" to 1.06f,
//                "CHF" to "KWD" to 0.35f,
//                "CHF" to "BHD" to 0.43f,
//                "CHF" to "OMR" to 0.44f,
//                "CHF" to "JOD" to 0.82f,
//                "CHF" to "KYD" to 0.96f,
//
//                "EUR" to "BRL" to 6.04f,
//                "EUR" to "USD" to 1.12f,
//                "EUR" to "GBP" to 0.84f,
//                "EUR" to "CHF" to 0.95f,
//                "EUR" to "KWD" to 0.33f,
//                "EUR" to "BHD" to 0.41f,
//                "EUR" to "OMR" to 0.42f,
//                "EUR" to "JOD" to 0.77f,
//                "EUR" to "KYD" to 0.90f,
//
//                "KWD" to "BRL" to 18.88f,
//                "KWD" to "USD" to 3.26f,
//                "KWD" to "GBP" to 2.53f,
//                "KWD" to "CHF" to 2.82f,
//                "KWD" to "EUR" to 3.00f,
//                "KWD" to "BHD" to 1.23f,
//                "KWD" to "OMR" to 1.26f,
//                "KWD" to "JOD" to 2.31f,
//                "KWD" to "KYD" to 2.72f,
//
//                "BHD" to "BRL" to 15.33f,
//                "BHD" to "USD" to 2.65f,
//                "BHD" to "GBP" to 2.06f,
//                "BHD" to "CHF" to 2.29f,
//                "BHD" to "KWD" to 0.81f,
//                "BHD" to "EUR" to 2.44f,
//                "BHD" to "OMR" to 1.02f,
//                "BHD" to "JOD" to 1.88f,
//                "BHD" to "KYD" to 2.21f,
//
//                "OMR" to "BRL" to 15.03f,
//                "OMR" to "USD" to 2.60f,
//                "OMR" to "GBP" to 2.02f,
//                "OMR" to "CHF" to 2.25f,
//                "OMR" to "KWD" to 0.80f,
//                "OMR" to "BHD" to 0.98f,
//                "OMR" to "EUR" to 2.39f,
//                "OMR" to "JOD" to 1.84f,
//                "OMR" to "KYD" to 2.16f,
//
//                "JOD" to "BRL" to 8.16f,
//                "JOD" to "USD" to 1.41f,
//                "JOD" to "GBP" to 1.09f,
//                "JOD" to "CHF" to 1.22f,
//                "JOD" to "KWD" to 0.43f,
//                "JOD" to "BHD" to 0.53f,
//                "JOD" to "OMR" to 0.54f,
//                "JOD" to "EUR" to 1.30f,
//                "JOD" to "KYD" to 1.17f,
//
//                "KYD" to "BRL" to 6.95f,
//                "KYD" to "USD" to 1.20f,
//                "KYD" to "GBP" to 0.93f,
//                "KYD" to "CHF" to 1.04f,
//                "KYD" to "KWD" to 0.37f,
//                "KYD" to "BHD" to 0.45f,
//                "KYD" to "OMR" to 0.46f,
//                "KYD" to "JOD" to 0.85f,
//                "KYD" to "EUR" to 1.10f,
//            )
//
//            val rate: Float = exchangeRates[coinType1 to coinType2]!!.toFloat()
//            result = rate * value
//        }
//        return result
//    }
