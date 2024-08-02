package com.example.conversaomoedas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.conversaomoedas.objects.Currency
import com.example.listadecontatos.R
import com.example.listadecontatos.databinding.ConversionPageBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ConversionPageBinding
private lateinit var currenciesList: Array<String>
private var initialValue: Double = 0.0
private val currency: Currency = Currency()

class ConversionPage : ComponentActivity() {
    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ConversionPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.getBundleExtra("bundle")

        currenciesList = bundle?.getStringArray("currenciesList")!!
        val currencyOne = Currency.getCurrencyAbbreviation(currenciesList[0])
        val currencyTwo = Currency.getCurrencyAbbreviation(currenciesList[1])

        initialValue = bundle.getDouble("initialValue")
        binding.initialValue.text = String.format("%.2f $currencyOne", initialValue)
        var controlInitialValue = initialValue
        var finalValue = 1.0

        when (currencyOne) {
            "BRL" -> {
                controlInitialValue *= 1.0
                binding.flagOne.setImageResource(R.drawable.brflag)
                binding.flagOne.contentDescription = "Ícone da Bandeira do Brasil"
            }

            "USD" -> {
                controlInitialValue *= 5.3
                binding.flagOne.setImageResource(R.drawable.usflag)
                binding.flagOne.contentDescription = "Ícone da bandeira dos Estados Unidos"
            }

            "GBP" -> {
                controlInitialValue *= 6.74
                binding.flagOne.setImageResource(R.drawable.ukflag)
                binding.flagOne.contentDescription = "Ícone da bandeira do Reino Unido"
            }

            "CHF" -> {
                controlInitialValue *= 5.91
                binding.flagOne.setImageResource(R.drawable.chflag)
                binding.flagOne.contentDescription = "Ícone da bandeira da Suíça"
            }

            "EUR" -> {
                controlInitialValue *= 5.72
                binding.flagOne.setImageResource(R.drawable.euflag)
                binding.flagOne.contentDescription = "Ícone da bandeira da União Europeia"
            }
        }

        when (currencyTwo) {
            "BRL" -> {
                finalValue = 1.0
                binding.flagTwo.setImageResource(R.drawable.brflag)
                binding.flagTwo.contentDescription = "Ícone da Bandeira do Brasil"
            }

            "USD" -> {
                finalValue = 5.3
                binding.flagTwo.setImageResource(R.drawable.usflag)
                binding.flagTwo.contentDescription = "Ícone da bandeira dos Estados Unidos"
            }

            "GBP" -> {
                finalValue = 6.74
                binding.flagTwo.setImageResource(R.drawable.ukflag)
                binding.flagTwo.contentDescription = "Ícone da bandeira do Reino Unido"
            }

            "CHF" -> {
                finalValue = 5.91
                binding.flagTwo.setImageResource(R.drawable.chflag)
                binding.flagTwo.contentDescription = "Ícone da bandeira da Suíça"
            }

            "EUR" -> {
                finalValue = 5.72
                binding.flagTwo.setImageResource(R.drawable.euflag)
                binding.flagTwo.contentDescription = "Ícone da bandeira da União Europeia"
            }
        }

        finalValue = controlInitialValue/finalValue
        binding.finalValue.text = String.format("%.2f $currencyTwo", finalValue)

        binding.returnButton.setOnClickListener { goToMainActivity() }

    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            val bundle = Bundle().apply {
                putStringArray("currenciesList", currenciesList)
                putDouble("initialValue", initialValue)
            }
            putExtra("bundle", bundle)
        }
        startActivity(intent)
    }
}