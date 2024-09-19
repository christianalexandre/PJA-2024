package com.example.conversaomoedas.objects.conversion_page

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.conversaomoedas.objects.Currency
import com.example.conversaomoedas.objects.home_screen.HomeScreenActivity
import com.example.listadecontatos.R
import com.example.listadecontatos.databinding.ActivityConversionPageBinding
import java.text.NumberFormat

class ConversionPageActivity : ComponentActivity() {

    private lateinit var binding: ActivityConversionPageBinding
    private lateinit var conversionPageViewModel: ConversionPageViewModel
    private lateinit var initialCurrency: String
    private lateinit var finalCurrency: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConversionPageBinding.inflate(layoutInflater)
        conversionPageViewModel = ViewModelProvider(this)[ConversionPageViewModel::class.java]

        getExtras()

        with(conversionPageViewModel) {
            convertedValue = initialValue
            convertValues(Currency.getCurrencyAbbreviation(initialCurrency), Currency.getCurrencyAbbreviation(finalCurrency))
        }

        setupView()
        setupListeners()
    }

    private fun getExtras() {
        val bundle = intent.getBundleExtra("bundle") ?: return

        initialCurrency = bundle.getString("initialCurrency") ?: return
        finalCurrency = bundle.getString("finalCurrency") ?: return
        conversionPageViewModel.initialValue = bundle.getDouble("initialValue")
    }

    private fun setupView() {
        setContentView(binding.root)

        setupCurrencyView(initialCurrency, binding.flagOne, binding.initialValue, conversionPageViewModel.initialValue)
        setupCurrencyView(finalCurrency, binding.flagTwo, binding.finalValue, conversionPageViewModel.finalValue)
    }

    @SuppressLint("SetTextI18n")
    private fun setupCurrencyView(currency: String, flag: ImageView, textView: TextView, value: Double) {
        when (currency) {
            "BRL" -> {
                flag.setImageResource(R.drawable.flag_br)
                flag.contentDescription = "Ícone da Bandeira do Brasil"
            }

            "USD" -> {
                flag.setImageResource(R.drawable.flag_us)
                flag.contentDescription = "Ícone da bandeira dos Estados Unidos"
            }

            "GBP" -> {
                flag.setImageResource(R.drawable.flag_uk)
                flag.contentDescription = "Ícone da bandeira do Reino Unido"
            }

            "CHF" -> {
                flag.setImageResource(R.drawable.flag_ch)
                flag.contentDescription = "Ícone da bandeira da Suíça"
            }

            "EUR" -> {
                flag.setImageResource(R.drawable.flag_eu)
                flag.contentDescription = "Ícone da bandeira da União Europeia"
            }
        }

        textView.text = "${formatCurrencyValue(value)} $currency"
    }

    private fun formatCurrencyValue(value: Double): String {
        var formattedValue = String.format("%.2f", value)

        formattedValue = formattedValue.replace(",", ".")
        formattedValue = NumberFormat.getNumberInstance().format(formattedValue.toDouble())

        val numberWithNoDecimalPlacesPattern = "^(.[^,]*)$".toRegex()
        val numberWithOneDecimalPlacePattern = "^(.*)(,)(\\d)$".toRegex()

        if(numberWithNoDecimalPlacesPattern.matches(formattedValue)) formattedValue += ",00"
        if(numberWithOneDecimalPlacePattern.matches(formattedValue)) formattedValue += "0"

        return formattedValue
    }

    private fun setupListeners() {
        binding.returnButton.setOnClickListener { goToMainActivity() }
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, HomeScreenActivity::class.java).apply {
            putExtra("bundle", Bundle().apply {
                putStringArray("currenciesList", currenciesList)
            })
        })
    }
}