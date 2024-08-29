package com.example.conversaomoedas

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.conversaomoedas.objects.Currency
import com.example.conversaomoedas.objects.Currency.getCurrencyAbbreviation
import com.example.conversaomoedas.objects.TextWatcherHelper.filterTextChangedForInitialValue
import com.example.conversaomoedas.objects.TextWatcherHelper.formatTextChangedForInitialValue
import com.example.listadecontatos.R
import com.example.listadecontatos.databinding.ActivityHomeScreenBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlin.properties.Delegates


class HomeScreen : ComponentActivity() {
    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var bundle: Bundle
    private lateinit var initialCurrencySpinner: Spinner
    private lateinit var finalCurrencySpinner: Spinner
    private lateinit var initialCurrencySpinnerSelectedItem: String
    private lateinit var finalCurrencySpinnerSelectedItem: String
    private lateinit var initialValueEditText: TextInputEditText
    private lateinit var initialValueInputLayout: TextInputLayout
    private var convertingValue by Delegates.notNull<Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        initialCurrencySpinner = binding.currencyOne
        finalCurrencySpinner = binding.currencyTwo
        initialCurrencySpinnerSelectedItem = "Selecionar uma moeda"
        finalCurrencySpinnerSelectedItem = "Selecionar uma moeda"
        initialValueEditText = binding.initialValueEditText
        initialValueInputLayout = binding.initialValueInputLayout

        getExtras()

        setupSpinners()
        initialValueInputLayout.prefixText = getCurrencyAbbreviation(initialCurrencySpinnerSelectedItem)

        setupListeners()

        initialValueEditText.setText("") // for some reason textWatcher doesn't work on first change in initialValue, so i put the first change here. now all changes will be watched by textWatcher
        setContentView(binding.root)
    }

    private fun getExtras() {
        bundle = intent.getBundleExtra("bundle") ?: return
        initialCurrencySpinnerSelectedItem = bundle.getStringArray("currenciesList")!![0]
        finalCurrencySpinnerSelectedItem = bundle.getStringArray("currenciesList")!![1]
    }

    private fun setupSpinners() {
        ArrayAdapter.createFromResource(
            this,
            R.array.array_currencies,
            R.layout.item_spinner
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
            initialCurrencySpinner.adapter = adapter
            finalCurrencySpinner.adapter = adapter
        }

        Currency.defineCurrencySelectedItem(
            initialCurrencySpinner,
            initialCurrencySpinnerSelectedItem
        )

        Currency.defineCurrencySelectedItem(finalCurrencySpinner, finalCurrencySpinnerSelectedItem)
    }

    private fun setupSpinnersSelectedItems() {
        initialCurrencySpinnerSelectedItem = initialCurrencySpinner.selectedItem.toString()
        finalCurrencySpinnerSelectedItem = finalCurrencySpinner.selectedItem.toString()
    }

    private fun setupListeners() {
        binding.calculateButton.setOnClickListener {
            setupSpinnersSelectedItems()

            if (verifyConditionsToGoToConversionPage()) return@setOnClickListener

            convertingValue = initialValueEditText.text.toString().replace("(\\.)".toRegex(), "").replace("(,)".toRegex(), ".").toDouble()

            goToConversionPage()
        }

        initialValueEditText.setOnClickListener {
            initialValueEditText.setSelection(initialValueEditText.text.toString().length)
        }

        initialValueEditText.addTextChangedListener(filterTextChangedForInitialValue(initialValueEditText))
        initialValueEditText.addTextChangedListener(formatTextChangedForInitialValue(initialValueEditText))

        initialCurrencySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                initialValueInputLayout.prefixText = getCurrencyAbbreviation(initialCurrencySpinner.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        initialValueEditText.setOnKeyListener { _, keyCode, event ->
                if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    setupSpinnersSelectedItems()

                    if (verifyConditionsToGoToConversionPage()) return@setOnKeyListener false

                    convertingValue = initialValueEditText.text.toString().replace("(\\.)".toRegex(), "").replace("(,)".toRegex(), ".").toDouble()

                    goToConversionPage()
                }
                false
        }

        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            binding.root.getWindowVisibleDisplayFrame(rect)

            val screenHeight = binding.root.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            val isKeyboardVisible = keypadHeight > screenHeight * 0.15

            Log.e("coiso", "dentro do viewTree + edittext está focado: ${initialValueEditText.isFocused}")

            if(isKeyboardVisible) {
                if(!initialValueEditText.isFocused) initialValueEditText.requestFocus()
                Log.e("coiso", "está visivel!")
            } else {
                initialValueEditText.clearFocus()
                Log.e("coiso", "não está visivel!")
            }
        }
    }

    private fun verifyConditionsToGoToConversionPage(): Boolean {
        if (!verifyIsBlank()) return true
        if (!verifyIsMissingSelectedCurrencies()) return true
        if (!verifyIsIdenticalSelectedCurrencies()) return true
        return false
    }

    private fun verifyIsBlank(): Boolean {
        if (initialValueEditText.text.toString().isBlank()) {
            Toast.makeText(this, R.string.missing_convert_value, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun verifyIsMissingSelectedCurrencies(): Boolean {
        if (initialCurrencySpinnerSelectedItem == "Selecionar uma moeda" || finalCurrencySpinnerSelectedItem == "Selecionar uma moeda") {
            Toast.makeText(this, R.string.missing_selected_currencies, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun verifyIsIdenticalSelectedCurrencies(): Boolean {
        if (initialCurrencySpinnerSelectedItem == finalCurrencySpinnerSelectedItem) {
            Toast.makeText(this, R.string.identical_currencies, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun goToConversionPage() {
        startActivity(Intent(this, ConversionPage::class.java).apply {
            putExtra("bundle", Bundle().apply {
                putStringArray(
                    "currenciesList",
                    arrayOf(initialCurrencySpinnerSelectedItem, finalCurrencySpinnerSelectedItem)
                )
                putDouble("initialValue", convertingValue)
            })
        })
    }
}