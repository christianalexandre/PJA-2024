package com.example.conversaomoedas.objects.home_screen

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.conversaomoedas.objects.conversion_page.ConversionPageActivity
import com.example.conversaomoedas.objects.Currency
import com.example.conversaomoedas.objects.Currency.getCurrencyAbbreviation
import com.example.listadecontatos.R
import com.example.listadecontatos.databinding.ActivityHomeScreenBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlin.properties.Delegates


class HomeScreenActivity : ComponentActivity() {
    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var bundle: Bundle
    private lateinit var homeScreenViewModel: HomeScreenViewModel
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
        homeScreenViewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]
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
        initialCurrencySpinnerSelectedItem = bundle.getString("initialCurrency") ?: return
        finalCurrencySpinnerSelectedItem = bundle.getString("finalCurrency") ?: return
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

            if (!verifyIsAbleToGoToConversionPage()) return@setOnClickListener

            convertingValue = initialValueEditText.text.toString().replace("(\\.)".toRegex(), "").replace("(,)".toRegex(), ".").toDouble()

            goToConversionPage()
        }

        initialValueEditText.setOnClickListener {
            initialValueEditText.setSelection(initialValueEditText.text.toString().length)
        }

        initialValueEditText.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) initialValueEditText.setSelection(initialValueEditText.text.toString().length)
        }

        initialValueEditText.addTextChangedListener(homeScreenViewModel.filterTextChangedForInitialValue(initialValueEditText))
        initialValueEditText.addTextChangedListener(homeScreenViewModel.formatTextChangedForInitialValue(initialValueEditText))
        initialValueEditText.addTextChangedListener(homeScreenViewModel.disableErrorModeOnTextChanged(initialValueInputLayout))

        initialCurrencySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                initialValueInputLayout.prefixText = getCurrencyAbbreviation(initialCurrencySpinner.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        initialValueEditText.setOnKeyListener { _, keyCode, event ->
                if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    setupSpinnersSelectedItems()

                    if (!verifyIsAbleToGoToConversionPage()) return@setOnKeyListener false

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
            val isKeyboardVisible = keypadHeight > (screenHeight * 0.15)

            if(isKeyboardVisible) {
                if(!initialValueEditText.isFocused) initialValueInputLayout.requestFocus()
                initialValueInputLayout.prefixText = getCurrencyAbbreviation(initialCurrencySpinner.selectedItem.toString())
            } else {
                initialValueInputLayout.clearFocus()
                if(initialValueEditText.length() == 0) initialValueInputLayout.prefixText = ""
            }
        }
    }

    private fun verifyIsAbleToGoToConversionPage(): Boolean {
        if (verifyIsBlank()) return false
        if (verifyIsMissingSelectedCurrencies()) return false
        if (verifyIsIdenticalSelectedCurrencies()) return false
        return true
    }

    private fun verifyIsBlank(): Boolean {
        if (initialValueEditText.text.toString().isBlank()) {
            initialValueInputLayout.error = resources.getText(R.string.missing_convert_value)
            return true
        }
        return false
    }

    private fun verifyIsMissingSelectedCurrencies(): Boolean {
        if (initialCurrencySpinnerSelectedItem == "Selecionar uma moeda" || finalCurrencySpinnerSelectedItem == "Selecionar uma moeda") {
            initialValueInputLayout.error = resources.getText(R.string.missing_selected_currencies)
            return true
        }
        return false
    }

    private fun verifyIsIdenticalSelectedCurrencies(): Boolean {
        if (initialCurrencySpinnerSelectedItem == finalCurrencySpinnerSelectedItem) {
            initialValueInputLayout.error = resources.getText(R.string.identical_currencies)
            return true
        }
        return false
    }

    private fun goToConversionPage() {
        startActivity(Intent(this, ConversionPageActivity::class.java).apply {
            putExtra("bundle", Bundle().apply {
                putString("initialCurrency", initialCurrencySpinnerSelectedItem)
                putString("finalCurrency", finalCurrencySpinnerSelectedItem)
                putDouble("initialValue", convertingValue)
            })
        })
    }
}