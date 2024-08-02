package com.example.conversaomoedas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.listadecontatos.R
import com.example.listadecontatos.databinding.MainActivityBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: MainActivityBinding
    private lateinit var currenciesList: Array<String>
    private lateinit var spinnerOne: Spinner
    private lateinit var spinnerTwo: Spinner
    private lateinit var initialValue: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityValues()
        setupView()
        setupListeners()
        getExtras()
    }

    @SuppressLint("ResourceType")
    private fun initActivityValues() {
        binding = MainActivityBinding.inflate(layoutInflater)
        spinnerOne = binding.currencyOne
        spinnerTwo = binding.currencyTwo
        initialValue = binding.initialValue
        ArrayAdapter.createFromResource(
            this,
            R.array.array_currencies,
            R.drawable.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.drawable.spinner_dropdown_item)
            spinnerOne.adapter = adapter
            spinnerTwo.adapter = adapter
        }
    }

    private fun setupView() {
        setContentView(binding.root)
    }

    private fun getExtras() {
        val bundle = intent.getBundleExtra("bundle")

        if (bundle != null) {
            val initialValue = bundle.getDouble("initialValue")
            val spinnerOneSelectedItem = bundle.getStringArray("currenciesList")!![0]
            val spinnerTwoSelectedItem = bundle.getStringArray("currenciesList")!![1]

            this.initialValue.setText(initialValue.toBigDecimal().toPlainString())

            with(Currency) {
                defineCurrencySelectedItem(spinnerOne, spinnerOneSelectedItem)
                defineCurrencySelectedItem(spinnerTwo, spinnerTwoSelectedItem)
            }
        }
    }

    private fun setupListeners() {
        binding.calculateButton.setOnClickListener { goToConversionPage() }
        initialValue.addTextChangedListener(TextWatcherHelper.filterChangedText(initialValue))
    }

    private fun goToConversionPage() {
        val spinnerOneSelectedItem = spinnerOne.selectedItem.toString()
        val spinnerTwoSelectedItem = spinnerTwo.selectedItem.toString()
        val convertingValue: Double
        currenciesList = arrayOf(spinnerOneSelectedItem, spinnerTwoSelectedItem)

        if (this.initialValue.text.toString().isBlank()) {
            Toast.makeText(this, R.string.missing_convert_value, Toast.LENGTH_SHORT).show()
            return
        } else {
            try {
                convertingValue = this.initialValue.text.toString().toDouble()
            } catch (_: NumberFormatException) {
                Toast.makeText(this, R.string.valid_value, Toast.LENGTH_SHORT).show()
                return
            }
        }

        if (spinnerOneSelectedItem == "Selecionar uma moeda" || spinnerTwoSelectedItem == "Selecionar uma moeda") {
            Toast.makeText(this, R.string.missing_selected_currencies, Toast.LENGTH_SHORT).show()
        } else if (convertingValue <= 0) {
            Toast.makeText(this, R.string.valid_value, Toast.LENGTH_SHORT).show()
        } else if (spinnerOneSelectedItem == spinnerTwoSelectedItem) {
            Toast.makeText(this, R.string.identical_currencies, Toast.LENGTH_SHORT).show()
        } else {
            setupExtras(convertingValue)
        }
    }

    private fun setupExtras(convertingValue: Double) {
        startActivity(Intent(this, ConversionPage::class.java).apply {
            val bundle = Bundle().apply {
                putStringArray("currenciesList", currenciesList)
                putDouble("initialValue", convertingValue)
            }
            putExtra("bundle", bundle)
        })
    }
}