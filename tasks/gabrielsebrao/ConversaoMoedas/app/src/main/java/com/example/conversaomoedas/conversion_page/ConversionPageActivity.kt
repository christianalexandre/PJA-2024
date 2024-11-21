package com.example.conversaomoedas.conversion_page

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.conversaomoedas.classes.Currency
import com.example.conversaomoedas.classes.CurrencyEnum
import com.example.conversaomoedas.home_screen.HomeScreenActivity
import com.example.conversaomoedasapi.R
import com.example.conversaomoedasapi.databinding.ActivityConversionPageBinding
import java.text.NumberFormat
import java.util.Locale
import androidx.lifecycle.lifecycleScope
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class ConversionPageActivity : ComponentActivity() {

    private lateinit var binding: ActivityConversionPageBinding
    private lateinit var conversionPageViewModel: ConversionPageViewModel
    private var disposable: Disposable? = null

    private lateinit var initialCurrency: Currency
    private lateinit var finalCurrency: Currency

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        window.statusBarColor = ContextCompat.getColor(this, R.color.dark_gray)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.dark_gray)

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

        // Observar o LiveData de loading
        conversionPageViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.loading.visibility = TextView.VISIBLE
                binding.currencyView.visibility = TextView.GONE
            } else {
                binding.loading.visibility = TextView.GONE
                binding.currencyView.visibility = TextView.VISIBLE
            }
        }

        // Observar o LiveData de sucesso
        conversionPageViewModel.conversionSuccess.observe(this) { success ->
            if (success) {
                setupCurrencyView(
                    initialCurrency.currency.getCode(resources),
                    binding.flagOne,
                    binding.initialValue,
                    initialCurrency.value
                )

                setupCurrencyView(
                    finalCurrency.currency.getCode(resources),
                    binding.flagTwo,
                    binding.finalValue,
                    conversionPageViewModel.finalValue
                )
            }
        }

        // Observar o LiveData de erro
        conversionPageViewModel.hasError.observe(this) { hasError ->
            if(hasError) {
                binding.connectionError.visibility = TextView.VISIBLE
            }
        }

        conversionPageViewModel.convertValues()
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