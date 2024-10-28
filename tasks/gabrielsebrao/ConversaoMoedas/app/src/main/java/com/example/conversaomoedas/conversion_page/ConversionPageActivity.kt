package com.example.conversaomoedas.conversion_page

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.conversaomoedas.classes.Connection
import com.example.conversaomoedas.classes.Currency
import com.example.conversaomoedas.classes.CurrencyEnum
import com.example.conversaomoedas.home_screen.HomeScreenActivity
import com.example.conversaomoedasapi.R
import com.example.conversaomoedasapi.databinding.ActivityConversionPageBinding
import java.text.NumberFormat
import java.util.Locale

class ConversionPageActivity : ComponentActivity() {

    private lateinit var binding: ActivityConversionPageBinding
    private lateinit var conversionPageViewModel: ConversionPageViewModel
    private lateinit var connection: Connection

    private lateinit var initialCurrency: Currency
    private lateinit var finalCurrency: Currency

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityConversionPageBinding.inflate(layoutInflater)
        conversionPageViewModel = ViewModelProvider(this)[ConversionPageViewModel::class.java]
        conversionPageViewModel.resources = resources
        connection = Connection()

        initialCurrency = Currency()
        finalCurrency = Currency()

        if(!connection.checkForInternet(this)) Toast.makeText(this, R.string.no_connection, Toast.LENGTH_LONG).show()

        getExtras()
        finalCurrency.value = conversionPageViewModel.finalValue

        with(conversionPageViewModel) {
            convertedValue = initialCurrency.value
            convertValues(initialCurrency.currency.getCode(resources), finalCurrency.currency.getCode(resources))
            finalCurrency.value = finalValue
        }

        setupView()
        setupListeners()

    }

    private fun getExtras() {

        val bundle = intent.getBundleExtra(resources.getString(R.string.bundle)) ?: return

        initialCurrency.currency = CurrencyEnum.getCurrencyEnum(resources, bundle.getString(resources.getString(R.string.bundle_initial_currency)) ?: return) ?: return
        finalCurrency.currency = CurrencyEnum.getCurrencyEnum(resources, bundle.getString(resources.getString(R.string.bundle_final_currency)) ?: return) ?: return
        initialCurrency.value = bundle.getDouble(resources.getString(R.string.bundle_initial_value))

    }

    private fun setupView() {

        setContentView(binding.root)

        setupCurrencyView(initialCurrency.currency.getCode(resources), binding.flagOne, binding.initialValue, initialCurrency.value)
        setupCurrencyView(finalCurrency.currency.getCode(resources), binding.flagTwo, binding.finalValue, conversionPageViewModel.finalValue)

    }

    private fun setupCurrencyView(currencyCode: String, flag: ImageView, textView: TextView, value: Double) {

        textView.text = String.format(Locale("pt", "BR"), "%s %s", formatCurrencyValue(value), currencyCode)

        val currency = CurrencyEnum.entries.firstOrNull { it.getCode(resources) == currencyCode }

        if(currency == null) {
            flag.setImageResource(CurrencyEnum.DEFAULT.currencyIcon)
            flag.contentDescription = CurrencyEnum.DEFAULT.getIconAlt(resources)
            return
        }

        flag.setImageResource(currency.currencyIcon)
        flag.contentDescription = currency.getIconAlt(resources)

    }

    private fun formatCurrencyValue(value: Double): String {

        var formattedValue = String.format(Locale("pt", "BR"), "%.2f", value)

        formattedValue = formattedValue.replace(",", ".")
        formattedValue = NumberFormat.getNumberInstance().format(formattedValue.toDoubleOrNull() ?: 0.0)

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
            putExtra(resources.getString(R.string.bundle), Bundle().apply {
                putString(resources.getString(R.string.bundle_initial_currency), initialCurrency.currency.getName(resources))
                putString(resources.getString(R.string.bundle_final_currency), finalCurrency.currency.getName(resources))
            })
        })

    }

}