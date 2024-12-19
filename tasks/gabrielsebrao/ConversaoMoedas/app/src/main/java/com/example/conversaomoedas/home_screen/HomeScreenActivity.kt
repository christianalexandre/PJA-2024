package com.example.conversaomoedas.home_screen

import com.example.conversaomoedas.classes.MapAdapter
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.conversaomoedas.classes.Connection
import com.example.conversaomoedas.conversion_page.ConversionPageActivity
import com.example.conversaomoedas.classes.currency.Currency
import com.example.conversaomoedas.classes.currency.CurrencyEnum
import com.example.conversaomoedasapi.R
import com.example.conversaomoedasapi.databinding.ActivityHomeScreenBinding


class HomeScreenActivity : ComponentActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var bundle: Bundle
    private lateinit var homeScreenViewModel: HomeScreenViewModel

    private lateinit var initialCurrency: Currency
    private lateinit var finalCurrency: Currency

    private lateinit var availableCurrenciesMap: Map<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        window.statusBarColor = ContextCompat.getColor(this, R.color.dark_gray)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.dark_gray)

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        homeScreenViewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]

        initialCurrency = Currency()
        finalCurrency = Currency()
        availableCurrenciesMap = mapOf()

        homeScreenViewModel.setAvailableCurrenciesMap()

        getExtras()

        binding.initialValueInputLayout.prefixText = initialCurrency.currency.getCode(resources)

        setupListeners()

        // for some reason textWatcher doesn't work on first change in initialValue, so i put the first change here. now all changes will be watched by textWatcher.
        binding.initialValueEditText.setText("")

        homeScreenViewModel.reqSuccess.observe(this) { reqSuccess ->

            if(reqSuccess) {
                availableCurrenciesMap = homeScreenViewModel.availableCurrenciesMap

            }

        }

        setContentView(binding.root)

    }

    private fun getExtras() {

        bundle = intent.getBundleExtra(resources.getString(R.string.bundle)) ?: Bundle()
        initialCurrency.currency = CurrencyEnum.getCurrencyEnum(resources, bundle.getString(resources.getString(R.string.bundle_initial_currency)) ?: return) ?: CurrencyEnum.DEFAULT
        finalCurrency.currency = CurrencyEnum.getCurrencyEnum(resources, bundle.getString(resources.getString(R.string.bundle_final_currency)) ?: return) ?: CurrencyEnum.DEFAULT

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
        setupCurrencyButtonsListeners()
        setupConvertingValueEditTextListeners()
        setupInitialCurrencySpinnerListeners()
        setupRootListeners()

    }

    private fun setupCalculateButtonListeners() {

        binding.calculateButton.setOnClickListener {
            initialCurrency.currency = CurrencyEnum.getCurrencyEnum(resources, binding.initialCurrencyButton.text.toString()) ?: CurrencyEnum.DEFAULT
            finalCurrency.currency= CurrencyEnum.getCurrencyEnum(resources, binding.finalCurrencyButton.text.toString()) ?: CurrencyEnum.DEFAULT

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
            .setTitle("Título")
            .setMessage("Esta é uma mensagem de diálogo.")
            .setPositiveButton("OK") { _, _ ->
                Toast.makeText(this, "OK clicado!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .setView(dialogView)
            .create()

        binding.initialCurrencyButton.setOnClickListener {
            recyclerView.adapter = MapAdapter(availableCurrenciesMap)
            dialog.show()
            Log.d("teste", availableCurrenciesMap.toString())
            Log.d("teste", recyclerView.adapter?.itemCount.toString())
        }


    }

    private fun setupConvertingValueEditTextListeners() {

        binding.initialValueEditText.setOnKeyListener { _, keyCode, event ->

            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                initialCurrency.currency = CurrencyEnum.getCurrencyEnum(resources, binding.initialCurrencyButton.text.toString()) ?: CurrencyEnum.DEFAULT
                finalCurrency.currency = CurrencyEnum.getCurrencyEnum(resources, binding.finalCurrencyButton.text.toString()) ?: CurrencyEnum.DEFAULT

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

    private fun setupInitialCurrencySpinnerListeners() {

        // quando o usuario mudar pela primeira vez o "Selecione uma moeda", remover essa opção das próximas vezes
        // ou resolver isso de outra forma

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

            initialCurrency.currency = CurrencyEnum.getCurrencyEnum(resources, binding.initialCurrencyButton.text.toString()) ?: CurrencyEnum.DEFAULT
            binding.initialValueInputLayout.prefixText = initialCurrency.currency.getCode(resources)

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

        if (initialCurrency.currency.getName(resources) == finalCurrency.currency.getName(resources)) {
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
                putString(resources.getString(R.string.bundle_initial_currency), initialCurrency.currency.getName(resources))
                putString(resources.getString(R.string.bundle_final_currency), finalCurrency.currency.getName(resources))
                putDouble(resources.getString(R.string.bundle_initial_value), initialCurrency.value)
            })
        })

    }

}