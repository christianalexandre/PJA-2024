package com.example.conversaomoedas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.listadecontatos.R
import com.example.listadecontatos.databinding.MainActivityBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: MainActivityBinding
private lateinit var currenciesList: Array<String>
@SuppressLint("StaticFieldLeak")
private lateinit var spinnerOne: Spinner

@SuppressLint("StaticFieldLeak")
private lateinit var spinnerTwo: Spinner
private val currency: Currency = Currency()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinnerOne = binding.currencyOne
        spinnerTwo = binding.currencyTwo
        binding.calculateButton.setOnClickListener { goToConversionPage() }

        ArrayAdapter.createFromResource(
            this,
            R.array.array_currencies,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerOne.adapter = adapter
            spinnerTwo.adapter = adapter
        }

        val bundle = intent.getBundleExtra("bundle")
        if(bundle != null)  {
            val initialValue = bundle.getDouble("initialValue")
            val spinnerOneSelectedItem = bundle.getStringArray("currenciesList")!![0]
            val spinnerTwoSelectedItem = bundle.getStringArray("currenciesList")!![1]

            if(initialValue % 1 == 0.0)
                binding.initialValue.setText(initialValue.toInt().toString())
            else
                binding.initialValue.setText(initialValue.toString())

        initialValue.addTextChangedListener(TextWatcherHelper.filterChangedText(initialValue))
    }

    private fun goToConversionPage() {

        val spinnerOneSelectedItem = spinnerOne.selectedItem.toString()
        val spinnerTwoSelectedItem = spinnerTwo.selectedItem.toString()
        var initialValue = 1.0
        currenciesList = arrayOf(spinnerOneSelectedItem, spinnerTwoSelectedItem)

        if (binding.initialValue.text.toString().isBlank()) {
            Toast.makeText(this, "Digite um valor a ser convertido.", Toast.LENGTH_SHORT).show()
            return
        } else {
            try {
                initialValue = binding.initialValue.text.toString().toDouble()
            } catch(_: NumberFormatException) {
                Toast.makeText(this, "Digite um valor válido.", Toast.LENGTH_SHORT).show()
                return
            }
        }

        if (spinnerOneSelectedItem == "Selecionar uma moeda" || spinnerTwoSelectedItem == "Selecionar uma moeda") {
            Toast.makeText(this, "Selecione alguma moeda para os dois campos.", Toast.LENGTH_SHORT)
                .show()
        } else if (initialValue <= 0) {
            Toast.makeText(this, "Digite um valor válido.", Toast.LENGTH_SHORT).show()
        } else if (spinnerOneSelectedItem == spinnerTwoSelectedItem) {
            Toast.makeText(this, "Selecione duas moedas diferentes.", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, ConversionPage::class.java).apply {
                val bundle = Bundle().apply {
                    putStringArray("currenciesList", currenciesList)
                    putDouble("initialValue", initialValue)
                }
                putExtra("bundle", bundle)
            }
            startActivity(intent)
        }
    }
}