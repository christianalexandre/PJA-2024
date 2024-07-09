package com.example.apimoedas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.apimoedas.databinding.MainActivityBinding
import retrofit2.Response

@SuppressLint("StaticFieldLeak")
private lateinit var binding: MainActivityBinding

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitService =
            RetrofitInstance.getRetrofitInstance().create(CurrencyApi::class.java)

        val responseLiveData: LiveData<Response<CurrencyItem>> =
            liveData {
                val response = retrofitService.getCurrencies()
                emit(response)
            }

        responseLiveData.observe(this) {
            val currencyInfo = it.body()

            val currencyName = "Currency name: ${currencyInfo?.code} \n"
            binding.textView.append(currencyName)
        })

    }
}