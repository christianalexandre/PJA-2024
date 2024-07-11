package com.example.apimoedas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.apimoedas.databinding.MainActivityBinding
import retrofit2.Response
import kotlin.text.*
import java.net.UnknownHostException

@SuppressLint("StaticFieldLeak")
private lateinit var binding: MainActivityBinding

class MainActivity : ComponentActivity() {
    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitService = RetrofitInstance.getRetrofitInstance().create(CurrencyApi::class.java)

        val responseLiveData: LiveData<Response<Currency>> =
            liveData {
                try {
                    val response = retrofitService.getCurrencies()
                    emit(response)
                } catch(_: UnknownHostException) {
                    binding.currencyOne.text = "teste"
                }
            }

        responseLiveData.observe(this) {
            val currencyInfo = it.body()
            binding.currencyOne.append("Currency name: ${currencyInfo?.USDBRL?.code} \n")
            binding.currencyOne.append(
                "Converted value to BRL: ${
                    String.format(
                        "R$%.2f",
                        currencyInfo?.USDBRL?.bid?.toDouble()
                    )
                }"
            )

            binding.currencyTwo.append("Currency name: ${currencyInfo?.EURBRL?.code} \n")
            binding.currencyTwo.append(
                "Converted value to BRL: ${
                    String.format(
                        "R$%.2f",
                        currencyInfo?.EURBRL?.bid?.toDouble()
                    )
                }"
            )

            binding.currencyThree.append("Currency name: ${currencyInfo?.CHFBRL?.code} \n")
            binding.currencyThree.append(
                "Converted value to BRL: ${
                    String.format(
                        "R$%.2f",
                        currencyInfo?.CHFBRL?.bid?.toDouble()
                    )
                }"
            )

            binding.currencyFour.append("Currency name: ${currencyInfo?.GBPBRL?.code} \n")
            binding.currencyFour.append(
                "Converted value to BRL: ${
                    String.format(
                        "R$%.2f",
                        currencyInfo?.GBPBRL?.bid?.toDouble()
                    )
                }"
            )
        }
    }
}