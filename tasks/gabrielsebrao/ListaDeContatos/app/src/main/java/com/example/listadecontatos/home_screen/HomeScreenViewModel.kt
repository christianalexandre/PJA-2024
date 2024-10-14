package com.example.listadecontatos.home_screen

import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.ViewModel


class HomeScreenViewModel : ViewModel() {

    fun filterTextChangedForName(editText: EditText): TextWatcher {

        return object : TextWatcher {

            private val namePattern = "^([\\p{L}\\s]{0,15})$".toRegex()

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
                    var result = dest.subSequence(0, dstart).toString() + source.subSequence(
                        begin,
                        end
                    ) + dest.subSequence(dend, dest.length).toString()

                    result = result.replace(" ", "")
                    result = result.replace("-", "")

                    if (!telephoneNumberPattern.matches(result)) return@InputFilter ""

                    null
                })
            }

            override fun afterTextChanged(s: Editable?) {}

        }

    }

    fun formatTextChangedForTelephoneNumber(editText: EditText): TextWatcher {

        return object : TextWatcher {

            private var formattedText = ""
            private val prefixPhoneNumberPattern = "^(\\d{2})$".toRegex()

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(textChanged: CharSequence?, start: Int, before: Int, count: Int) {

                Log.e("coiso", before.toString())

                formattedText = textChanged.toString()

                if(before == 1) {
                    Log.e("coiso", "chegou no before")
                    formattedText = formattedText.dropLast(1)
                }

                Log.e("coiso", formattedText)

                editText.removeTextChangedListener(this)

                if(prefixPhoneNumberPattern.matches(formattedText)) {
                    Log.e("coiso", "chegou aqui")
                    editText.setText("$formattedText ")
                    editText.setSelection(editText.length())

                    editText.addTextChangedListener(this)
                    return
                }

                editText.setText(formattedText)
                editText.setSelection(editText.length())

                editText.addTextChangedListener(this)

            }

            override fun afterTextChanged(s: Editable?) {}

        }

    }
}