package com.example.conversaomoedas.classes

import android.content.res.Resources
import com.example.conversaomoedasapi.R

class Currency(private val resources: Resources) {

    var name: String = resources.getString(R.string.currency_default)

        set(name) {

            field = name

            val value = CurrencyEnum.entries.firstOrNull { it.getName(resources) == field }

            if(value == null) {
                field = "Selecionar uma moeda"
                this.code = "$"
                return
            }

            this.code = value.getCode(resources)

        }

    var code: String = resources.getString(R.string.currency_code_default)
    var value: Double = 0.0

}