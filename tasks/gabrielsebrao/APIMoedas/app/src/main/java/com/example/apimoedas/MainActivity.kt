package com.example.apimoedas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.apimoedas.databinding.MainActivityBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("StaticFieldLeak")
private lateinit var binding: MainActivityBinding

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView2.setText(R.string.currencyTwo)
        binding.textView3.setText(R.string.initialValue)
    }

    fun getApiConversaoMoedaData() {
        val retrofitClient = ApiConversaoMoeda
        val endpoint = retrofitClient.create(ApiConversaoMoedaEndpoint::class.java)
    }
}