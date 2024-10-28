package com.example.conversaomoedas.conversion_page

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.example.conversaomoedasapi.R

class  ConversionPageViewModel: ViewModel() {

    var convertedValue: Double = 0.0
    var finalValue: Double = 0.0
    lateinit var resources: Resources

    fun convertValues(initialCurrency: String, finalCurrency: String) {

        when (initialCurrency) {

            resources.getString(R.string.currency_code_brazilian_real) -> { convertedValue *= 1.00 }

            resources.getString(R.string.currency_code_american_dollar) -> { convertedValue *= 5.3 }

            resources.getString(R.string.currency_code_british_pound) -> { convertedValue *= 6.74 }

            resources.getString(R.string.currency_code_swiss_franc) -> { convertedValue *= 5.91 }

            resources.getString(R.string.currency_code_euro) -> { convertedValue *= 5.72 }

            resources.getString(R.string.currency_code_chinese_yuan) -> { convertedValue *= 0.80 }

            resources.getString(R.string.currency_code_japanese_yen) -> { convertedValue *= 0.037 }

            resources.getString(R.string.currency_code_canadian_dollar) -> { convertedValue *= 4.09 }

            resources.getString(R.string.currency_code_bitcoin) -> { convertedValue *= 386_491.49 }

            resources.getString(R.string.currency_code_dogecoin) -> { convertedValue *= 0.81 }
        }

        when (finalCurrency) {

            resources.getString(R.string.currency_code_brazilian_real) -> { finalValue = convertedValue }

            resources.getString(R.string.currency_code_american_dollar) -> { finalValue = convertedValue / 5.3 }

            resources.getString(R.string.currency_code_british_pound) -> { finalValue = convertedValue / 6.74 }

            resources.getString(R.string.currency_code_swiss_franc) -> { finalValue = convertedValue / 5.91 }

            resources.getString(R.string.currency_code_euro) -> { finalValue = convertedValue / 5.72 }

            resources.getString(R.string.currency_code_chinese_yuan) -> { finalValue = convertedValue / 0.80 }

            resources.getString(R.string.currency_code_japanese_yen) -> { finalValue = convertedValue / 0.037 }

            resources.getString(R.string.currency_code_canadian_dollar) -> { finalValue = convertedValue / 4.09 }

            resources.getString(R.string.currency_code_bitcoin) -> { finalValue = convertedValue / 386_491.49 }

            resources.getString(R.string.currency_code_dogecoin) -> { finalValue = convertedValue / 0.81 }

        }

    }

}