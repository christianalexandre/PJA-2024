package com.example.conversaomoedas.conversion_page

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.example.conversaomoedasapi.R

class  ConversionPageViewModel: ViewModel() {

    var convertedValue: Double = 0.0
    var finalValue: Double = 0.0
    lateinit var resources: Resources

    // consume api later.
    fun convertValues(initialCurrency: String, finalCurrency: String) {

        when (initialCurrency) {
            resources.getString(R.string.currency_code_brazilian_real) -> { convertedValue *= 1.0 }

            resources.getString(R.string.currency_code_american_dollar) -> { convertedValue *= 5.3 }

            resources.getString(R.string.currency_code_british_pound) -> { convertedValue *= 6.74 }

            resources.getString(R.string.currency_code_swiss_franc) -> { convertedValue *= 5.91 }

            resources.getString(R.string.currency_code_euro) -> { convertedValue *= 5.72 }
        }

        when (finalCurrency) {
            resources.getString(R.string.currency_code_brazilian_real) -> { finalValue = convertedValue }

            resources.getString(R.string.currency_code_american_dollar) -> { finalValue = convertedValue / 5.3 }

            resources.getString(R.string.currency_code_british_pound) -> { finalValue = convertedValue / 6.74 }

            resources.getString(R.string.currency_code_swiss_franc) -> { finalValue = convertedValue / 5.91 }

            resources.getString(R.string.currency_code_euro) -> { finalValue = convertedValue / 5.72 }
        }

    }

}