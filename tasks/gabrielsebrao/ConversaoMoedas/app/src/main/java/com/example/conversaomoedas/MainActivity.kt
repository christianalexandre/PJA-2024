package com.example.conversaomoedas

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.conversaomoedas.objects.Currency
import com.example.conversaomoedas.objects.TextWatcherHelper
import com.example.listadecontatos.R
import com.example.listadecontatos.databinding.MainActivityBinding
import kotlin.properties.Delegates

class MainActivity : ComponentActivity() {
    private lateinit var binding: MainActivityBinding
    private lateinit var bundle: Bundle
    private lateinit var spinnerOne: Spinner
    private lateinit var spinnerTwo: Spinner
    private lateinit var spinnerOneSelectedItem: String
    private lateinit var spinnerTwoSelectedItem: String
    private lateinit var initialValue: EditText
    private var convertingValue by Delegates.notNull<Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityVariables()
        getExtras()
        setupSpinners()
        setupListeners()

        initialValue.setText("") // for some reason textWatcher doesn't work on first change in initialValue, so i put the first change here. now all changes will fall into textWatcher
        setContentView(binding.root)
    }

    private fun initActivityVariables() {
        binding = MainActivityBinding.inflate(layoutInflater)
        spinnerOne = binding.currencyOne
        spinnerTwo = binding.currencyTwo
        spinnerOneSelectedItem = "Selecionar uma moeda"
        spinnerTwoSelectedItem = "Selecionar uma moeda"
        initialValue = binding.initialValue
    }

    private fun getExtras() {
        bundle = intent.getBundleExtra("bundle") ?: return
        spinnerOneSelectedItem = bundle.getStringArray("currenciesList")!![0]
        spinnerTwoSelectedItem = bundle.getStringArray("currenciesList")!![1]
    }

    private fun setupSpinners() {
        ArrayAdapter.createFromResource(
            this,
            R.array.array_currencies,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            spinnerOne.adapter = adapter
            spinnerTwo.adapter = adapter
        }

        Currency.defineCurrencySelectedItem(spinnerOne, spinnerOneSelectedItem)
        Currency.defineCurrencySelectedItem(spinnerTwo, spinnerTwoSelectedItem)
    }

    private fun setupListeners() {
        binding.calculateButton.setOnClickListener {
            spinnerOneSelectedItem = spinnerOne.selectedItem.toString()
            spinnerTwoSelectedItem = spinnerTwo.selectedItem.toString()

            if(!verifyIsBlank()) return@setOnClickListener
            if(!verifyIsMissingSelectedCurrencies()) return@setOnClickListener
            if(!verifyIsIdenticalSelectedCurrencies()) return@setOnClickListener

            convertingValue = initialValue.text.toString().replace(',','.').toDouble()

            goToConversionPage()
        }
        initialValue.addTextChangedListener(TextWatcherHelper.filterChangedText(initialValue))
    }

    private fun verifyIsBlank(): Boolean {
        if (initialValue.text.toString().isBlank()) {
            Toast.makeText(this, R.string.missing_convert_value, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun verifyIsMissingSelectedCurrencies(): Boolean {
        if (spinnerOneSelectedItem == "Selecionar uma moeda" || spinnerTwoSelectedItem == "Selecionar uma moeda") {
            Toast.makeText(this, R.string.missing_selected_currencies, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun verifyIsIdenticalSelectedCurrencies(): Boolean {
        if (spinnerOneSelectedItem == spinnerTwoSelectedItem) {
            Toast.makeText(this, R.string.identical_currencies, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun goToConversionPage() {
        startActivity(Intent(this, ConversionPage::class.java).apply {
            putExtra("bundle", Bundle().apply {
                putStringArray("currenciesList", arrayOf(spinnerOneSelectedItem, spinnerTwoSelectedItem))
                putDouble("initialValue", convertingValue)
            })
        })
    }
}