package com.example.conversaomoedas.conversion_page

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.lifecycle.ViewModelProvider
import com.example.conversaomoedas.classes.Currency
import com.example.conversaomoedas.classes.CurrencyEnum
import com.example.conversaomoedas.home_screen.HomeScreenActivity
import com.example.conversaomoedasapi.R
import com.example.conversaomoedasapi.databinding.ActivityConversionPageBinding
import java.text.NumberFormat
import java.util.Locale
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ConversionPageActivity : ComponentActivity() {

    private lateinit var binding: ActivityConversionPageBinding
    private lateinit var conversionPageViewModel: ConversionPageViewModel

    private lateinit var initialCurrency: Currency
    private lateinit var finalCurrency: Currency

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityConversionPageBinding.inflate(layoutInflater)

        initialCurrency = Currency()
        finalCurrency = Currency()

        getExtras()
        conversionPageViewModel = ViewModelProvider(this)[ConversionPageViewModel::class.java]
            .resources(resources)
            .currencies(initialCurrency.currency, finalCurrency.currency)

        finalCurrency.value = conversionPageViewModel.finalValue


        conversionPageViewModel.convertedValue = initialCurrency.value
        finalCurrency.value = conversionPageViewModel.finalValue

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

        lifecycleScope.launch {

            conversionPageViewModel.convertValues()

            binding.loading.visibility = TextView.GONE
            binding.currencyView.visibility = TextView.VISIBLE

            setupCurrencyView(initialCurrency.currency.getCode(resources), binding.flagOne, binding.initialValue, initialCurrency.value)
            setupCurrencyView(finalCurrency.currency.getCode(resources), binding.flagTwo, binding.finalValue, conversionPageViewModel.finalValue)

        }

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