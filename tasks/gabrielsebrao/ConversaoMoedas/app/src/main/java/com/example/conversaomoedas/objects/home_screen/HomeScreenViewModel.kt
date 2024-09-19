package com.example.conversaomoedas.objects.home_screen

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputLayout
import java.text.NumberFormat

class HomeScreenViewModel: ViewModel() {

    private var isDecimalNumberWithoutDecimalPlaces: Boolean = false
    private var previousResult: String = ""

    fun filterTextChangedForInitialValue(editText: EditText): TextWatcher {
        return object : TextWatcher {

            private var onlyOneCommaPattern = "^(,)$".toRegex()
            private var decimalNumberWithDoubleCommasPattern = "^([(\\d*).?]*)(,)(.*)(,)$".toRegex()
            private var decimalNumberThatExceedsTwoNumbersAfterCommaPattern = "^([(\\d*).?]*)(,)(\\d{2}.)$".toRegex()
            private val decimalNumberThatExceedsFourteenDigitsBeforeCommaPattern = "^([\\d.]{14})$".toRegex()

            override fun beforeTextChanged(s: CharSequence?, startO: Int, count: Int, after: Int) {}

            override fun onTextChanged(textChanged: CharSequence?, start: Int, before: Int, count: Int) {
                editText.filters = arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
                    val result = dest.subSequence(0, dstart).toString() + source.subSequence(start, end) + dest.subSequence(dend, dest.length).toString()

                    if (onlyOneCommaPattern.matches(result)) return@InputFilter ""
                    if (decimalNumberWithDoubleCommasPattern.matches(result)) return@InputFilter ""
                    if (decimalNumberThatExceedsFourteenDigitsBeforeCommaPattern.matches(result)) return@InputFilter ""
                    if (decimalNumberThatExceedsTwoNumbersAfterCommaPattern.matches(result)) return@InputFilter ""

                    null
                })

                editText.setSelection(editText.text.toString().length)
            }

            override fun afterTextChanged(textChanged: Editable?) {}
        }
    }

    fun formatTextChangedForInitialValue(editText: EditText): TextWatcher {
        return object : TextWatcher {

            private var current = ""
            private var decimalNumberWithOnlyACommaAndNothingThenPattern = "^([(\\d*).?]*)(,)$".toRegex()
            private var decimalNumberWithOneZeroAfterCommaPattern = "^(.*)(,0)$".toRegex()
            private var decimalNumberWithOneZeroAfterADecimalDigitPattern = "^(.*)(,\\d0)$".toRegex()

            private val decimalNumberWithOneDecimalPlace = "^([(\\d*).?]*)(,)(\\d)$".toRegex()
            private val integerNumber = "^[\\d.?]*$".toRegex()

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(textChanged: CharSequence?, start: Int, before: Int, count: Int) {

                textChanged ?: return
                if (textChanged.toString() != current) {

                    if (textChanged.toString().isEmpty()) return

                    if(isDecimalNumberWithoutDecimalPlaces && (!decimalNumberWithOneDecimalPlace.matches(textChanged) && !integerNumber.matches(textChanged))) {
                        editText.removeTextChangedListener(this)
                        editText.setText(previousResult)
                        editText.addTextChangedListener(this)
                    }

                    if (decimalNumberWithOnlyACommaAndNothingThenPattern.matches(textChanged.toString())) {
                        isDecimalNumberWithoutDecimalPlaces = true
                        previousResult = textChanged.toString()
                        return
                    }
                    isDecimalNumberWithoutDecimalPlaces = false

                    if (decimalNumberWithOneZeroAfterCommaPattern.matches(textChanged.toString())) return
                    if (decimalNumberWithOneZeroAfterADecimalDigitPattern.matches(textChanged.toString())) return

                    val cleanString = textChanged.toString().replace("(\\.)".toRegex(), "").replace("(,)".toRegex(), ".")
                    val parsed = cleanString.toDoubleOrNull() ?: 0.0
                    val formatted = NumberFormat.getNumberInstance().format(parsed)

                    editText.removeTextChangedListener(this)
                    current = formatted
                    editText.setText(formatted)
                    editText.setSelection(formatted.length)
                    editText.addTextChangedListener(this)

                    previousResult = formatted.toString()
                }
            }

            override fun afterTextChanged(textChanged: Editable?) {}
        }
    }

    fun disableErrorModeOnTextChanged(textInputLayout: TextInputLayout): TextWatcher {
        return object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(textChanged: CharSequence?, start: Int, before: Int, count: Int) {
                textInputLayout.error = ""
            }

            override fun afterTextChanged(textChanged: Editable?) {}
        }
    }
}