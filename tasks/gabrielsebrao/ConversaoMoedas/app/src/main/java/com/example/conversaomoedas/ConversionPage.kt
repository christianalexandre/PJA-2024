package com.example.conversaomoedas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.conversaomoedas.objects.Currency
import com.example.listadecontatos.R
import com.example.listadecontatos.databinding.ConversionPageBinding
import kotlin.properties.Delegates

class ConversionPage : ComponentActivity() {

    private lateinit var binding: ConversionPageBinding
    private lateinit var currencyOne: String
    private lateinit var currencyTwo: String
    private lateinit var currenciesList: Array<String>
    private var finalValue by Delegates.notNull<Double>()
    private var controlInitialValue by Delegates.notNull<Double>()
    private var initialValue by Delegates.notNull<Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityVariables()
        getExtras()
        setupView()
        setupListeners()
    }

    private fun initActivityVariables() {
        binding = ConversionPageBinding.inflate(layoutInflater)
        finalValue = 1.0
    }

    private fun getExtras() {
        val bundle = intent.getBundleExtra("bundle")

        currenciesList = bundle?.getStringArray("currenciesList")!!
        currencyOne = Currency.getCurrencyAbbreviation(currenciesList[0])
        currencyTwo = Currency.getCurrencyAbbreviation(currenciesList[1])

        initialValue = bundle.getDouble("initialValue")

        controlInitialValue = initialValue
    }

    private fun setupView() {
        setContentView(binding.root)

        setupCurrencyOneView()
        setupCurrencyTwoView()

        finalValue = controlInitialValue / finalValue

        binding.initialValue.text = String.format("%.2f $currencyOne", initialValue)
        binding.finalValue.text = String.format("%.2f $currencyTwo", finalValue)
    }

    private fun setupListeners() {
        binding.returnButton.setOnClickListener { goToMainActivity() }
    }

    private fun setupCurrencyOneView() {
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
    }

    private fun setupCurrencyTwoView() {
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
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            val bundle = Bundle().apply {
                putStringArray("currenciesList", currenciesList)
            }
            putExtra("bundle", bundle)
        })
    }
}