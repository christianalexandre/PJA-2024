package com.example.conversaomoedas.home_screen

import com.example.conversaomoedas.classes.InternetConnectionListener
import com.example.conversaomoedas.classes.MapAdapter
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.conversaomoedas.classes.Connection
import com.example.conversaomoedas.conversion_page.ConversionPageActivity
import com.example.conversaomoedas.classes.currency.Currency
import com.example.conversaomoedasapi.R
import com.example.conversaomoedasapi.databinding.ActivityHomeScreenBinding


class HomeScreenActivity : ComponentActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var bundle: Bundle
    private lateinit var homeScreenViewModel: HomeScreenViewModel

    private lateinit var initialCurrency: Currency
    private lateinit var finalCurrency: Currency

    private lateinit var availableCurrenciesMap: Map<String, String>
    private lateinit var internetConnectionListener: InternetConnectionListener

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        window.statusBarColor = ContextCompat.getColor(this, R.color.dark_gray)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.dark_gray)

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        homeScreenViewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]
        setContentView(binding.root)

        availableCurrenciesMap = mapOf()
        internetConnectionListener = InternetConnectionListener(this, availableCurrenciesMap, homeScreenViewModel)

        getExtras()

        binding.initialValueInputLayout.prefixText = initialCurrency.currencyCode

        setupListeners()

        // for some reason textWatcher doesn't work on first change in initialValue, so i put the first change here. now all changes will be watched by textWatcher.
        binding.initialValueEditText.setText("")

        homeScreenViewModel.reqSuccess.observe(this) { reqSuccess ->

            if(reqSuccess) {
                availableCurrenciesMap = homeScreenViewModel.availableCurrenciesMap
                internetConnectionListener.availableCurrenciesMap = availableCurrenciesMap
            }

        }

        homeScreenViewModel.selectedInitialCurrency.observe(this) { selectedCurrency ->

            initialCurrency.currencyName = selectedCurrency
            initialCurrency.setCurrencyCode()

            binding.initialCurrencyButton.text = initialCurrency.currencyName
            binding.initialValueInputLayout.prefixText = initialCurrency.currencyCode

        }

        homeScreenViewModel.selectedFinalCurrency.observe(this) { selectedCurrency ->

            binding.finalCurrencyButton.text = selectedCurrency

        }

    }

    override fun onResume() {
        super.onResume()
        internetConnectionListener.startListening()
    }

    override fun onPause() {
        super.onPause()
        internetConnectionListener.stopListening()
    }

    private fun getExtras() {

        bundle = intent.getBundleExtra(resources.getString(R.string.bundle)) ?: Bundle()

        if(bundle.isEmpty) {
            initialCurrency = Currency()
                .resources(resources)
            finalCurrency = Currency()
                .resources(resources)

            return
        }

        initialCurrency = bundle.getParcelable(resources.getString(R.string.bundle_initial_currency)) ?: return
        initialCurrency.resources(resources)
        homeScreenViewModel.selectedInitialCurrency.postValue(initialCurrency.currencyName)

        finalCurrency = bundle.getParcelable(resources.getString(R.string.bundle_final_currency)) ?: return
        finalCurrency.resources(resources)
        homeScreenViewModel.selectedFinalCurrency.postValue(finalCurrency.currencyName)

    }

    private fun setupListeners() {

        setupCalculateButtonListeners()
        setupCurrencyButtonsListeners()
        setupConvertingValueEditTextListeners()
        setupRootListeners()

    }

    private fun setupCalculateButtonListeners() {

        binding.calculateButton.setOnClickListener {
            initialCurrency.currencyName = binding.initialCurrencyButton.text.toString()
            initialCurrency.setCurrencyCode()

            finalCurrency.currencyName = binding.finalCurrencyButton.text.toString()
            finalCurrency.setCurrencyCode()

            if (!verifyIsAbleToGoToConversionPage())
                return@setOnClickListener

            initialCurrency.value = binding.initialValueEditText.text.toString().replace("(\\.)".toRegex(), "").replace("(,)".toRegex(), ".").toDoubleOrNull() ?: 0.0

            goToConversionPage()
        }

    }
    private fun setupCurrencyButtonsListeners() {

        val dialogView = layoutInflater.inflate(R.layout.item_dialog_available_currencies, null)

        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.recyclerViewDialog)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dialog = AlertDialog.Builder(this)
            .setCustomTitle(findViewById(R.id.titleDialog))
            .setView(dialogView)
            .create()

        binding.initialCurrencyButton.setOnClickListener {

            val dialogViewWithErrorConnection = dialogView.findViewById<TextView>(R.id.errorConnectionMessage)
            internetConnectionListener.availableCurrenciesMap = availableCurrenciesMap

            if(availableCurrenciesMap.isEmpty())
                dialogViewWithErrorConnection.visibility = View.VISIBLE
            else
                dialogViewWithErrorConnection.visibility = View.GONE

            recyclerView.adapter = MapAdapter(availableCurrenciesMap, dialog, homeScreenViewModel.selectedInitialCurrency)
            dialog.show()
        }

        binding.finalCurrencyButton.setOnClickListener {

            val dialogViewWithErrorConnection = dialogView.findViewById<TextView>(R.id.errorConnectionMessage)
            internetConnectionListener.availableCurrenciesMap = availableCurrenciesMap

            if(availableCurrenciesMap.isEmpty())
                dialogViewWithErrorConnection.visibility = View.VISIBLE
            else
                dialogViewWithErrorConnection.visibility = View.GONE

            recyclerView.adapter = MapAdapter(availableCurrenciesMap, dialog, homeScreenViewModel.selectedFinalCurrency)
            dialog.show()
        }

        if(bundle.isEmpty)
            return

        binding.initialCurrencyButton.text = initialCurrency.currencyName
        binding.finalCurrencyButton.text = finalCurrency.currencyName

    }

    private fun setupConvertingValueEditTextListeners() {

        binding.initialValueEditText.setOnKeyListener { _, keyCode, event ->

            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                initialCurrency.currencyName = binding.initialCurrencyButton.text.toString()
                initialCurrency.setCurrencyCode()

                finalCurrency.currencyName = binding.finalCurrencyButton.text.toString()
                initialCurrency.setCurrencyCode()

                if (!verifyIsAbleToGoToConversionPage())
                    return@setOnKeyListener false

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

            if(!binding.initialValueEditText.isFocused)
                binding.initialValueInputLayout.requestFocus()

            initialCurrency.currencyName = binding.initialCurrencyButton.text.toString()
            initialCurrency.setCurrencyCode()
            binding.initialValueInputLayout.prefixText = initialCurrency.currencyCode

        }

    }

    private fun verifyIsAbleToGoToConversionPage(): Boolean {

        if (verifyIsBlank())
            return false

        if (verifyAreMissingSelectedCurrencies())
            return false

        if (verifyAreIdenticalSelectedCurrencies())
            return false

        if (verifyIsDisconnected())
            return false

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

        if (binding.initialCurrencyButton.text == resources.getString(R.string.currency_default) || binding.finalCurrencyButton.text == resources.getString(R.string.currency_default)) {
            binding.initialValueInputLayout.error = resources.getText(R.string.missing_selected_currencies)
            return true
        }

        return false

    }

    private fun verifyAreIdenticalSelectedCurrencies(): Boolean {

        if (initialCurrency.currencyName == finalCurrency.currencyName) {
            binding.initialValueInputLayout.error = resources.getText(R.string.identical_currencies)
            return true
        }

        return false

    }

    private fun verifyIsDisconnected(): Boolean {

        if (Connection.isDisconnected(this)) {
            binding.initialValueInputLayout.error = resources.getText(R.string.no_connection)
            return true
        }

        return false
    }

    private fun goToConversionPage() {

        startActivity(Intent(this, ConversionPageActivity::class.java).apply {
            putExtra(resources.getString(R.string.bundle), Bundle().apply {
                putParcelable(resources.getString(R.string.bundle_initial_currency), initialCurrency)
                putParcelable(resources.getString(R.string.bundle_final_currency), finalCurrency)
            })
        })

    }

}