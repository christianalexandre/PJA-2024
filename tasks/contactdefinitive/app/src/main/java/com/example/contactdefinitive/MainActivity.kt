package com.example.contactdefinitive

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var selectedName1: String? = null
    private var selectedName2: String? = null

    private var coinType1: String? = null
    private var coinType2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListeners()
    }

    private fun setupListeners() {
        val valueField = findViewById<EditText>(R.id.edit_text3)
        val spinner: Spinner = findViewById(R.id.spin1)

        val items = resources.getStringArray(R.array.coins_array1)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                selectedName1 = parent.getItemAtPosition(position).toString()
                coinType1 = selectedName1
                Toast.makeText(this@MainActivity, "Valores padrões selecionados automáticamente", Toast.LENGTH_SHORT).show()
                Toast.makeText(this@MainActivity, "${selectedName1} foi selecionado", Toast.LENGTH_SHORT).show()
                //retirar o toast no inicio porem deixar ao selecionar uma moeda
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "Nada foi selecionado", Toast.LENGTH_SHORT).show()
            }
        }

        val spinner2: Spinner = findViewById(R.id.spin2)

        val items2 = resources.getStringArray(R.array.coins_array2)

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items2)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter2

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                selectedName2 = parent.getItemAtPosition(position).toString()
                coinType2 = selectedName2
                Toast.makeText(this@MainActivity, "Valores padrões selecionados automáticamente", Toast.LENGTH_SHORT).show()
                Toast.makeText(this@MainActivity, "${selectedName2} foi selecionado", Toast.LENGTH_SHORT).show()
                //retirar o toast no inicio
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "Nada foi selecionado", Toast.LENGTH_SHORT).show()
            }
        }

        val buttonCalculation = findViewById<Button>(R.id.buttonCalcular)

        buttonCalculation.setOnClickListener {
            val text = valueField.text.toString()
            val valor: Float = if (text != "") text.toFloat() else 0f

            openSecondActivity(valor)
            valueField.text.clear()
        }

        valueField.addTextChangedListener(TextWatcherHelper.textWatcher(valueField))
    }

    private fun openSecondActivity(valor: Float) {
        val intent = Intent(this, SecondActivity::class.java).apply {
            val bundle = Bundle().apply {
                putFloat("valor", valor)
                putString("coinType1", coinType1)
                putString("coinType2", coinType2)
            }
            putExtra("bundle", bundle)
        }
        startActivity(intent)
    }
}

object TextWatcherHelper {
    fun textWatcher(valueField: EditText): TextWatcher {
        return object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // No action needed
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //Desenvolve a regra com regex aqui
//                val regex = "(\\d*)(\\.?)(\\d{0,2}$)".toRegex()
//                val regex1 = "^(0|([1-9]\\d{0,8}))(\\.\\d{1,2})?$".toRegex()
//                val matchResult = regex1.find(valueField.text)
//                print(matchResult)

                val inputFilter = InputFilter { source, start, end, dest, dstart, dend ->
                    val pattern = "^\\d*\\.?\\d{0,2}$".toRegex()
                    val result = dest.subSequence(0, dstart).toString() +
                            source.subSequence(start, end) +
                            dest.subSequence(dend, dest.length).toString()
                    if (pattern.matches(result)) {
                        null // Permitir a entrada
                    } else {
                        "" // Rejeitar a entrada
                    }
                }
                valueField.filters = arrayOf(inputFilter)

            }

            override fun afterTextChanged(s: Editable) {
                // No action needed
            }
        }
    }
}