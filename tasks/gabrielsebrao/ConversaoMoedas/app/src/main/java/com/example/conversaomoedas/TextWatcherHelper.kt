package com.example.conversaomoedas

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText

object TextWatcherHelper {
    fun filterChangedText(text: EditText): TextWatcher {
        return object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val inputFilter = InputFilter { source, start, end, dest, dstart, dend ->
                    val pattern = "^\\d*\\.?\\d{0,2}$".toRegex()
                    val result = dest.subSequence(0, dstart).toString() + source.subSequence(start, end) + dest.subSequence(dend, dest.length).toString()
                    if(pattern.matches(result)) {
                        null
                    } else {
                        ""
                    }
                }

                text.filters = arrayOf(inputFilter)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
    }
}