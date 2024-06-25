package com.example.conversaomoedas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.listadecontatos.databinding.ConversionPageBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ConversionPageBinding

class ConversionPage : ComponentActivity() {
    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ConversionPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.getBundleExtra("bundle")
        val currencyOne = getCurrencyAbbreviation(bundle?.getStringArray("currenciesList")?.get(0)!!)
        val currencyTwo = getCurrencyAbbreviation(bundle.getStringArray("currenciesList")?.get(1)!!)
        val initialValue = bundle.getDouble("initialValue")
        val finalValue = calculateConversion(currencyOne, currencyTwo, initialValue)

        binding.initialValue.text = String.format("%.2f $currencyOne", initialValue)
        binding.finalValue.text = String.format("%.2f $currencyTwo", finalValue)

    }

    private fun calculateConversion(
        currencyOne: String,
        currencyTwo: String,
        initialValue: Double
    ): Double {
        val brl = 1.0
        val usd = 5.3
        val gbp = 6.74
        val chf = 5.91
        val eur = 5.72

        var valueOne = initialValue
        var valueTwo = 0.0

        when (currencyOne) {
            "BRL" -> {
                valueOne *= brl
            }

            "USD" -> {
                valueOne *= usd
            }

            "GBP" -> {
                valueOne *= gbp
            }

            "CHF" -> {
                valueOne *= chf
            }

            "EUR" -> {
                valueOne *= eur
            }
        }
        when (currencyTwo) {
            "BRL" -> {
                valueTwo = brl
            }

            "USD" -> {
                valueTwo = usd
            }

            "GBP" -> {
                valueTwo = gbp
            }

            "CHF" -> {
                valueTwo = chf
            }

            "EUR" -> {
                valueTwo = eur
            }
        }

        return valueOne / valueTwo
    }

    private fun getCurrencyAbbreviation(currency: String): String {
        when(currency) {
            "Real (BRL)" -> {
                return "BRL"
            }

            "Dólar Americano (USD)" -> {
                return "USD"
            }

            "Libra Esterlina (GBP)" -> {
                return "GBP"
            }

            "Franco Suíço (CHF)" -> {
                return "CHF"
            }

            "Euro (EUR)" -> {
                return "EUR"
            }
        }

        return "ERROR"
    }
}