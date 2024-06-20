package com.example.conversaomoedas

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.ComponentActivity
import com.example.listadecontatos.R
import com.example.listadecontatos.databinding.MainActivityBinding
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt

private lateinit var binding: MainActivityBinding

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinnerOne = binding.currencyOne
        val spinnerTwo = binding.currencyTwo

        ArrayAdapter.createFromResource(
            this,
            R.array.array_currencies,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerOne.adapter = adapter
            spinnerTwo.adapter = adapter
        }

        var spinnerOneSelectedItem = spinnerOne.selectedItem.toString()
        var spinnerTwoSelectedItem = spinnerTwo.selectedItem.toString()

        calculateConversion(spinnerOneSelectedItem, spinnerTwoSelectedItem, 10.0)
    }

    fun calculateConversion(currencyOne: String, currencyTwo:String, value:Double): Double {
        val brl = 1.0
        val usd = 5.3
        val gbp = 6.74
        val chf = 5.91
        val eur = 5.72

        var valueOne = value
        var valueTwo = 0.0

        when(currencyOne) {
            "Real (BRL)" -> {
                valueOne = brl
            }

            "Dólar Americano (USD)" -> {
                valueOne = usd
            }

            "Libra Esterlina (GBP)" -> {
                valueOne = gbp
            }

            "Franco Suíço" -> {
                valueOne = chf
            }

            "Euro (EUR)" -> {
                valueOne = eur
            }
        }

        when(currencyTwo) {
            "Real (BRL)" -> {
                valueTwo = brl
            }

            "Dólar Americano (USD)" -> {
                valueTwo = usd
            }

            "Libra Esterlina (GBP)" -> {
                valueTwo = gbp
            }

            "Franco Suíço" -> {
                valueTwo = chf
            }

            "Euro (EUR)" -> {
                valueTwo = eur
            }
        }

        return valueOne/valueTwo
    }
}