package com.example.conversaomoedas.home_screen

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.example.conversaomoedas.conversion_page.ConversionPageActivity
import com.example.conversaomoedas.classes.Currency
import com.example.conversaomoedas.classes.CurrencyEnum
import com.example.conversaomoedasapi.R
import com.example.conversaomoedasapi.databinding.ActivityHomeScreenBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class HomeScreenActivity : ComponentActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var bundle: Bundle
    private lateinit var homeScreenViewModel: HomeScreenViewModel

    private lateinit var initialCurrency: Currency
    private lateinit var finalCurrency: Currency

    private lateinit var arrayCurrency: Array<String>

    private lateinit var initialCurrencySpinner: Spinner
    private lateinit var finalCurrencySpinner: Spinner
    private lateinit var convertingValueEditText: TextInputEditText
    private lateinit var convertingValueInputLayout: TextInputLayout
    private lateinit var calculateButton: Button
    private lateinit var root: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        homeScreenViewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]

        initialCurrency = Currency(resources)
        finalCurrency = Currency(resources)

        arrayCurrency = resources.getStringArray(R.array.array_currencies)

        initialCurrencySpinner = binding.currencyOne
        finalCurrencySpinner = binding.currencyTwo
        convertingValueEditText = binding.initialValueEditText
        convertingValueInputLayout = binding.initialValueInputLayout
        calculateButton = binding.calculateButton
        root = binding.root

        getExtras()

        setupSpinners()
        convertingValueInputLayout.prefixText = initialCurrency.code

        setupListeners()

        // for some reason textWatcher doesn't work on first change in initialValue, so i put the first change here. now all changes will be watched by textWatcher.
        convertingValueEditText.setText("")

        setContentView(root)

    }

    private fun getExtras() {

        bundle = intent.getBundleExtra(resources.getString(R.string.bundle)) ?: Bundle()
        initialCurrency.name = bundle.getString(resources.getString(R.string.bundle_initial_currency)) ?: return
        finalCurrency.name = bundle.getString(resources.getString(R.string.bundle_final_currency)) ?: return

    }

    private fun setupSpinners() {

        var arrayStringResources = R.array.array_currencies_without_default_item

        if(bundle.isEmpty) arrayStringResources = R.array.array_currencies

        ArrayAdapter.createFromResource(this, arrayStringResources, R.layout.item_spinner).also { adapter ->
            adapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
            initialCurrencySpinner.adapter = adapter
            finalCurrencySpinner.adapter = adapter
        }

        defineSpinnerSelectedItem(initialCurrencySpinner, initialCurrency.name)
        defineSpinnerSelectedItem(finalCurrencySpinner, finalCurrency.name)

    }

    private fun defineSpinnerSelectedItem(spinner: Spinner, currency: String) {

        val value = CurrencyEnum.entries.firstOrNull { it.getName(resources) == currency }

        if(value == null) {
            spinner.setSelection(0)
            return
        }

        spinner.setSelection(CurrencyEnum.valueOf(value.toString()).ordinal - 1)

    }

    private fun setupListeners() {

        setupCalculateButtonListeners()
        setupConvertingValueEditTextListeners()
        setupInitialCurrencySpinnerListeners()
        setupRootListeners()

    }

    private fun setupCalculateButtonListeners() {

        calculateButton.setOnClickListener {
            initialCurrency.name = initialCurrencySpinner.selectedItem.toString()
            finalCurrency.name = finalCurrencySpinner.selectedItem.toString()

            if (!verifyIsAbleToGoToConversionPage()) return@setOnClickListener

            initialCurrency.value = convertingValueEditText.text.toString().replace("(\\.)".toRegex(), "").replace("(,)".toRegex(), ".").toDoubleOrNull() ?: 0.0

            goToConversionPage()
        }

    }

    private fun setupConvertingValueEditTextListeners() {

        convertingValueEditText.setOnKeyListener { _, keyCode, event ->

            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                initialCurrency.name = initialCurrencySpinner.selectedItem.toString()
                finalCurrency.name = finalCurrencySpinner.selectedItem.toString()

                if (!verifyIsAbleToGoToConversionPage()) return@setOnKeyListener false

                initialCurrency.value = convertingValueEditText.text.toString().replace("(\\.)".toRegex(), "").replace("(,)".toRegex(), ".").toDoubleOrNull() ?: 0.0

                goToConversionPage()
            }

            false // for some reason, if returns true it is not possible to digit on edittext anymore.

        }

        convertingValueEditText.setOnClickListener {

            convertingValueEditText.setSelection(convertingValueEditText.text.toString().length)

        }

        with(homeScreenViewModel) {
            convertingValueEditText.addTextChangedListener( filterTextChangedForInitialValue(convertingValueEditText) )
            convertingValueEditText.addTextChangedListener( formatTextChangedForInitialValue(convertingValueEditText) )
            convertingValueEditText.addTextChangedListener( disableErrorModeOnTextChanged(convertingValueInputLayout) )
        }

    }

    private fun setupInitialCurrencySpinnerListeners() {

        initialCurrencySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            private var selectionsOfDiferentItems = 0

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                initialCurrency.name = initialCurrencySpinner.selectedItem.toString()
                convertingValueInputLayout.prefixText = initialCurrency.code

                if(selectionsOfDiferentItems == 0) {
                    selectionsOfDiferentItems += 1
                    return
                }

                if(selectionsOfDiferentItems > 1) return

                ArrayAdapter.createFromResource(
                    this@HomeScreenActivity,
                    R.array.array_currencies_without_default_item,
                    R.layout.item_spinner
                ).also { adapter ->
                    adapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
                    initialCurrencySpinner.adapter = adapter
                }

                defineSpinnerSelectedItem(initialCurrencySpinner, initialCurrency.name)

                selectionsOfDiferentItems += 1

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

        finalCurrencySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            private var selectionsOfDifferentItems = 0

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                finalCurrency.name = finalCurrencySpinner.selectedItem.toString()

                if(selectionsOfDifferentItems == 0) {
                    selectionsOfDifferentItems += 1
                    return
                }

                if(selectionsOfDifferentItems > 1) return

                ArrayAdapter.createFromResource(
                    this@HomeScreenActivity,
                    R.array.array_currencies_without_default_item,
                    R.layout.item_spinner
                ).also { adapter ->
                    adapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
                    finalCurrencySpinner.adapter = adapter
                }

                defineSpinnerSelectedItem(finalCurrencySpinner, finalCurrency.name)

                selectionsOfDifferentItems += 1

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

    }

    private fun setupRootListeners() {

        root.viewTreeObserver.addOnGlobalLayoutListener {

            val rect = Rect()
            root.getWindowVisibleDisplayFrame(rect)

            val screenHeight = root.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            val isKeyboardVisible = keypadHeight > (screenHeight * 0.15)

            if(!isKeyboardVisible) {
                convertingValueInputLayout.clearFocus()
                if(convertingValueEditText.length() == 0) convertingValueInputLayout.prefixText = ""
                return@addOnGlobalLayoutListener
            }

            if(!convertingValueEditText.isFocused) convertingValueInputLayout.requestFocus()

            initialCurrency.name = initialCurrencySpinner.selectedItem.toString()
            convertingValueInputLayout.prefixText = initialCurrency.code

        }

    }

    private fun verifyIsAbleToGoToConversionPage(): Boolean {

        if (verifyIsBlank()) return false
        if (verifyAreMissingSelectedCurrencies()) return false
        if (verifyAreIdenticalSelectedCurrencies()) return false
        return true

    }

    private fun verifyIsBlank(): Boolean {

        if (convertingValueEditText.text.toString().isBlank()) {
            convertingValueInputLayout.error = resources.getText(R.string.missing_convert_value)
            return true
        }
        return false

    }

    private fun verifyAreMissingSelectedCurrencies(): Boolean {

        if (initialCurrency.name == resources.getString(R.string.currency_default) || finalCurrency.name == resources.getString(R.string.currency_default)) {
            convertingValueInputLayout.error = resources.getText(R.string.missing_selected_currencies)
            return true
        }
        return false

    }

    private fun verifyAreIdenticalSelectedCurrencies(): Boolean {

        if (initialCurrency.name == finalCurrency.name) {
            convertingValueInputLayout.error = resources.getText(R.string.identical_currencies)
            return true
        }
        return false

    }

    private fun goToConversionPage() {

        startActivity(Intent(this, ConversionPageActivity::class.java).apply {
            putExtra(resources.getString(R.string.bundle), Bundle().apply {
                putString(resources.getString(R.string.bundle_initial_currency), initialCurrency.name)
                putString(resources.getString(R.string.bundle_final_currency), finalCurrency.name)
                putDouble(resources.getString(R.string.bundle_initial_value), initialCurrency.value)
            })
        })

    }

}