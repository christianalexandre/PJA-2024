package com.example.conversaomoedas.objects

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat

object TextWatcherHelper {
    fun filterTextChangedForInitialValue(editText: EditText): TextWatcher {
        return object : TextWatcher {

            private var onlyOneCommaPattern = "^(,)$".toRegex()
            private var decimalNumberWithDoubleCommasPattern = "^([(\\d*).?]*)(,)(.*)(,)$".toRegex()
            private var decimalNumberThatExceedsTwoNumbersAfterCommaPattern = "^([(\\d*).?]*)(,)(\\d{2}.)$".toRegex()
            private val decimalNumberThatExceedsFourteenDigitsBeforeComma = "^([\\d.]{14})$".toRegex()

            override fun beforeTextChanged(s: CharSequence?, startO: Int, count: Int, after: Int) {}

            override fun onTextChanged(textChanged: CharSequence?, start: Int, before: Int, count: Int) {
                editText.filters = arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
                        val result = dest.subSequence(0, dstart).toString() + source.subSequence(start, end) + dest.subSequence(dend, dest.length).toString()

                        if (onlyOneCommaPattern.matches(result)) return@InputFilter ""
                        if (decimalNumberWithDoubleCommasPattern.matches(result)) return@InputFilter ""
                        if (decimalNumberThatExceedsFourteenDigitsBeforeComma.matches(result)) return@InputFilter ""
                        if (decimalNumberThatExceedsTwoNumbersAfterCommaPattern.matches(result)) return@InputFilter ""

                        editText.setSelection(editText.text.toString().length)
                        null
                    })
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

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(textChanged: CharSequence?, start: Int, before: Int, count: Int) {

                if (textChanged.toString() != current) {

                    if (textChanged.toString().isEmpty()) return
                    if (decimalNumberWithOnlyACommaAndNothingThenPattern.matches(textChanged.toString())) return
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
                }
            }

            override fun afterTextChanged(textChanged: Editable?) {}
        }
    }
}
