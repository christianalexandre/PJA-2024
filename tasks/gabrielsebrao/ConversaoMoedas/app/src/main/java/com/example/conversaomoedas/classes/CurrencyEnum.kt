package com.example.conversaomoedas.classes

import android.content.res.Resources
import com.example.conversaomoedasapi.R

enum class CurrencyEnum(private val currencyName: Int, private val currencyCode: Int, val currencyIcon: Int?, private val currencyIconAlt: Int?) {

    DEFAULT(R.string.currency_default, R.string.currency_code_default, null, null),
    BRAZILIAN_REAL(R.string.currency_brazilian_real, R.string.currency_code_brazilian_real, R.drawable.flag_br, R.string.icon_alt_brazilian_flag),
    AMERICAN_DOLLAR(R.string.currency_american_dollar, R.string.currency_code_american_dollar, R.drawable.flag_us, R.string.icon_alt_american_flag),
    CHINESE_YUAN(R.string.currency_chinese_yuan, R.string.currency_code_chinese_yuan, null, R.string.icon_alt_chinese_flag),
    BRITISH_POUND(R.string.currency_british_pound, R.string.currency_code_british_pound, R.drawable.flag_uk, R.string.icon_alt_british_flag),
    EURO(R.string.currency_euro, R.string.currency_code_euro, R.drawable.flag_eu, R.string.icon_alt_european_union_flag),
    SWISS_FRANC(R.string.currency_swiss_franc, R.string.currency_code_swiss_franc, R.drawable.flag_ch, R.string.icon_alt_swiss_flag),
    JAPANESE_YUAN(R.string.currency_japanese_yen, R.string.currency_code_japanese_yen, null, R.string.icon_alt_japanese_flag),
    CANADIAN_DOLLAR(R.string.currency_canadian_dollar, R.string.currency_code_canadian_dollar, null, R.string.icon_alt_canadian_flag),
    BITCOIN(R.string.currency_bitcoin, R.string.currency_code_bitcoin, null, R.string.icon_alt_bitcoin),
    DOGECOIN(R.string.currency_dogecoin, R.string.currency_code_dogecoin, null, R.string.icon_alt_dogecoin);

    fun getName(resources: Resources): String {
        return resources.getString(currencyName)
    }

    fun getCode(resources: Resources): String {
        return resources.getString(currencyCode)
    }

    fun getIconAlt(resources: Resources): String {

        if(currencyIconAlt == null) return ""

        return resources.getString(currencyIconAlt)
    }

}