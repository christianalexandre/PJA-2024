package com.example.conversaomoedas.home_screen

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.conversaomoedas.conversion_page.ConversionPageActivity
import com.example.conversaomoedas.classes.Currency
import com.example.conversaomoedas.classes.CurrencyEnum
import com.example.conversaomoedasapi.R
import com.example.conversaomoedasapi.databinding.ActivityHomeScreenBinding


class HomeScreenActivity : ComponentActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var bundle: Bundle
    private lateinit var homeScreenViewModel: HomeScreenViewModel

    private lateinit var initialCurrency: Currency
    private lateinit var finalCurrency: Currency

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        homeScreenViewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]

        initialCurrency = Currency()
        finalCurrency = Currency()

        getExtras()

        setupSpinners()
        binding.initialValueInputLayout.prefixText = initialCurrency.currency.getCode(resources)

        setupListeners()

        // for some reason textWatcher doesn't work on first change in initialValue, so i put the first change here. now all changes will be watched by textWatcher.
        binding.initialValueEditText.setText("")

        setContentView(binding.root)

    }

    private fun getExtras() {

        bundle = intent.getBundleExtra(resources.getString(R.string.bundle)) ?: Bundle()
        initialCurrency.currency = CurrencyEnum.getCurrencyEnum(resources, bundle.getString(resources.getString(R.string.bundle_initial_currency)) ?: return) ?: CurrencyEnum.DEFAULT
        finalCurrency.currency = CurrencyEnum.getCurrencyEnum(resources, bundle.getString(resources.getString(R.string.bundle_final_currency)) ?: return) ?: CurrencyEnum.DEFAULT

    }

    private fun setupSpinners() {

        var arrayStringResources = R.array.array_currencies_without_default_item

        if(bundle.isEmpty) arrayStringResources = R.array.array_currencies

        ArrayAdapter.createFromResource(this, arrayStringResources, R.layout.item_spinner).also { adapter ->
            adapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
            binding.initialCurrencySpinner.adapter = adapter
            binding.finalCurrencySpinner.adapter = adapter
        }

        defineSpinnerSelectedItem(binding.initialCurrencySpinner, initialCurrency.currency.getName(resources))
        defineSpinnerSelectedItem(binding.finalCurrencySpinner, finalCurrency.currency.getName(resources))

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

        binding.calculateButton.setOnClickListener {
            initialCurrency.currency = CurrencyEnum.getCurrencyEnum(resources, binding.initialCurrencySpinner.selectedItem.toString()) ?: CurrencyEnum.DEFAULT
            finalCurrency.currency= CurrencyEnum.getCurrencyEnum(resources, binding.finalCurrencySpinner.selectedItem.toString()) ?: CurrencyEnum.DEFAULT

            if (!verifyIsAbleToGoToConversionPage()) return@setOnClickListener

            initialCurrency.value = binding.initialValueEditText.text.toString().replace("(\\.)".toRegex(), "").replace("(,)".toRegex(), ".").toDoubleOrNull() ?: 0.0

            goToConversionPage()
        }

    }

    private fun setupConvertingValueEditTextListeners() {

        binding.initialValueEditText.setOnKeyListener { _, keyCode, event ->

            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                initialCurrency.currency = CurrencyEnum.getCurrencyEnum(resources, binding.initialCurrencySpinner.selectedItem.toString()) ?: CurrencyEnum.DEFAULT
                finalCurrency.currency = CurrencyEnum.getCurrencyEnum(resources, binding.finalCurrencySpinner.selectedItem.toString()) ?: CurrencyEnum.DEFAULT

                if (!verifyIsAbleToGoToConversionPage()) return@setOnKeyListener false

                initialCurrency.value = binding.initialValueEditText.text.toString().replace("(\\.)".toRegex(), "").replace("(,)".toRegex(), ".").toDoubleOrNull() ?: 0.0

                goToConversionPage()
            }

            false // for some reason, if returns true it is not possible to digit on edittext anymore.

        }

        binding.initialValueEditText.setOnClickListener {

            binding.initialValueEditText.setSelection(binding.initialValueEditText.text.toString().length)

        }

        with(homeScreenViewModel) {
            binding.initialValueEditText.addTextChangedListener( filterTextChangedForInitialValue(binding.initialValueEditText) )
            binding.initialValueEditText.addTextChangedListener( formatTextChangedForInitialValue(binding.initialValueEditText) )
            binding.initialValueEditText.addTextChangedListener( disableErrorModeOnTextChanged(binding.initialValueInputLayout) )
        }

    }

    private fun setupInitialCurrencySpinnerListeners() {

        binding.initialCurrencySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            private var selectionsOfDiferentItems = 0

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                initialCurrency.currency = CurrencyEnum.getCurrencyEnum(resources, binding.initialCurrencySpinner.selectedItem.toString()) ?: CurrencyEnum.DEFAULT
                binding.initialValueInputLayout.prefixText = initialCurrency.currency.getCode(resources)

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
                    binding.initialCurrencySpinner.adapter = adapter
                }

                defineSpinnerSelectedItem(binding.initialCurrencySpinner, initialCurrency.currency.getName(resources))

                selectionsOfDiferentItems += 1

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

        binding.finalCurrencySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            private var selectionsOfDifferentItems = 0

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                finalCurrency.currency = CurrencyEnum.getCurrencyEnum(resources, binding.finalCurrencySpinner.selectedItem.toString()) ?: CurrencyEnum.DEFAULT

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
                    binding.finalCurrencySpinner.adapter = adapter
                }

                defineSpinnerSelectedItem(binding.finalCurrencySpinner, finalCurrency.currency.getName(resources))

                selectionsOfDifferentItems += 1

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

    }

    private fun setupRootListeners() {

        binding.root.viewTreeObserver.addOnGlobalLayoutListener {

            val rect = Rect()
            binding.root.getWindowVisibleDisplayFrame(rect)

            val screenHeight = binding.root.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            val isKeyboardVisible = keypadHeight > (screenHeight * 0.15)

            if(!isKeyboardVisible) {
                binding.initialValueInputLayout.clearFocus()
                if(binding.initialValueEditText.length() == 0) binding.initialValueInputLayout.prefixText = ""
                return@addOnGlobalLayoutListener
            }

            if(!binding.initialValueEditText.isFocused) binding.initialValueInputLayout.requestFocus()

            initialCurrency.currency = CurrencyEnum.getCurrencyEnum(resources, binding.initialCurrencySpinner.selectedItem.toString()) ?: CurrencyEnum.DEFAULT
            binding.initialValueInputLayout.prefixText = initialCurrency.currency.getCode(resources)

        }

    }

    private fun verifyIsAbleToGoToConversionPage(): Boolean {

        if (verifyIsBlank()) return false
        if (verifyAreMissingSelectedCurrencies()) return false
        if (verifyAreIdenticalSelectedCurrencies()) return false
        return true

    }

    private fun verifyIsBlank(): Boolean {

        if (binding.initialValueEditText.text.toString().isBlank()) {
            binding.initialValueInputLayout.error = resources.getText(R.string.missing_convert_value)
            return true
        }
        return false

    }

    private fun verifyAreMissingSelectedCurrencies(): Boolean {

        if (binding.initialCurrencySpinner.selectedItem == resources.getString(R.string.currency_default) || binding.finalCurrencySpinner.selectedItem == resources.getString(R.string.currency_default)) {
            binding.initialValueInputLayout.error = resources.getText(R.string.missing_selected_currencies)
            return true
        }
        return false

    }

    private fun verifyAreIdenticalSelectedCurrencies(): Boolean {

        if (initialCurrency.currency.getName(resources) == finalCurrency.currency.getName(resources)) {
            binding.initialValueInputLayout.error = resources.getText(R.string.identical_currencies)
            return true
        }
        return false

    }

    private fun goToConversionPage() {

        startActivity(Intent(this, ConversionPageActivity::class.java).apply {
            putExtra(resources.getString(R.string.bundle), Bundle().apply {
                putString(resources.getString(R.string.bundle_initial_currency), initialCurrency.currency.getName(resources))
                putString(resources.getString(R.string.bundle_final_currency), finalCurrency.currency.getName(resources))
                putDouble(resources.getString(R.string.bundle_initial_value), initialCurrency.value)
            })
        })

    }

}