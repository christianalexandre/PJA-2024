package com.example.listadecontatos.home_screen

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.ViewModel
import java.util.Locale


class HomeScreenViewModel : ViewModel() {

    fun filterTextChangedForName(editText: EditText): TextWatcher {

        return object : TextWatcher {

            private val namePattern = "^([\\p{L}\\s]{0,30})$".toRegex()

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editText.filters = arrayOf(InputFilter { source, begin, end, dest, dstart, dend ->
                    val result = dest.subSequence(0, dstart).toString() + source.subSequence(
                        begin,
                        end
                    ) + dest.subSequence(dend, dest.length).toString()

                    if (!namePattern.matches(result)) return@InputFilter ""

                    null
                })
            }

            override fun afterTextChanged(s: Editable?) {}

        }

    }

    fun filterTextChangedForTelephoneNumber(editText: EditText): TextWatcher {

        return object : TextWatcher {

            private val telephoneNumberPattern = "^(\\d{0,11})$".toRegex()

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editText.filters = arrayOf(InputFilter { source, begin, end, dest, dstart, dend ->

                    var result = dest.subSequence(0, dstart).toString() + source.subSequence(begin, end) + dest.subSequence(dend, dest.length).toString()

                    result = result.replace(" ", "").replace("-", "")

                    if (!telephoneNumberPattern.matches(result)) return@InputFilter ""

                    null

                })
            }

            override fun afterTextChanged(s: Editable?) {}

        }

    }

    fun maskTextChangedForTelephoneNumber(editText: EditText): TextWatcher {

        return object : TextWatcher {

            private var inputText = ""
            private val prefixPhoneNumberPattern = "^(\\d{2})$".toRegex()
            private val firstPartResidentialPhoneNumberPattern = "^(\\d{2}(\\x20)(\\d{4}))$".toRegex()
            private val notFormattedPersonalPhoneNumberPattern = "^(\\d{2}(\\x20)(\\d{4})(-)(\\d{5}))$".toRegex()
            private val RESIDENTIAL_PHONE_NUMBER_LENGTH = 12

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(textChanged: CharSequence?, start: Int, before: Int, count: Int) {

                inputText = textChanged.toString()

                editText.removeTextChangedListener(this)

                if(before == 1 && prefixPhoneNumberPattern.matches(inputText)) {
                    inputText = inputText.dropLast(1)
                    editText.setText(inputText)
                    editText.setSelection(editText.length())
                }

                if(before == 1 && firstPartResidentialPhoneNumberPattern.matches(inputText)) {
                    inputText = inputText.dropLast(1)
                    editText.setText(inputText)
                    editText.setSelection(editText.length())
                }

                if(before == 1 && inputText.length == RESIDENTIAL_PHONE_NUMBER_LENGTH) {
                    inputText = inputText.replace("-", "")
                    inputText = inputText.substring(0, 7) + "-" + inputText.substring(7)
                    editText.setText(inputText)
                    editText.setSelection(editText.length())
                }

                if(prefixPhoneNumberPattern.matches(inputText)) {
                    editText.setText(String.format(Locale("pt", "BR"), "$inputText "))
                    editText.setSelection(editText.length())
                }

                if(firstPartResidentialPhoneNumberPattern.matches(inputText)) {
                    editText.setText(String.format(Locale("pt", "BR"), "$inputText-"))
                    editText.setSelection(editText.length())
                }

                if(notFormattedPersonalPhoneNumberPattern.matches(inputText)) {
                    inputText = inputText.replace("-", "")
                    inputText = inputText.substring(0, 8) + "-" + inputText.substring(8)
                    editText.setText(inputText)
                    editText.setSelection(editText.length())
                }

                editText.addTextChangedListener(this)

            }

            override fun afterTextChanged(s: Editable?) {}

        }

    }
}