package com.example.conversaomoedas.classes

import android.content.res.Resources
import com.example.conversaomoedasapi.R

enum class CurrencyEnum(private val currencyName: Int, private val currencyCode: Int, val currencyIcon: Int, private val currencyIconAlt: Int, var valueToReal: Double) {

    DEFAULT(R.string.currency_default, R.string.currency_code_default, R.drawable.icon_currency_default, R.string.icon_alt_default, 0.00),
    BRAZILIAN_REAL(R.string.currency_brazilian_real, R.string.currency_code_brazilian_real, R.drawable.flag_br, R.string.icon_alt_brazilian_flag, 1.00),
    AMERICAN_DOLLAR(R.string.currency_american_dollar, R.string.currency_code_american_dollar, R.drawable.flag_us, R.string.icon_alt_american_flag, 5.30),
    CHINESE_YUAN(R.string.currency_chinese_yuan, R.string.currency_code_chinese_yuan, R.drawable.flag_cn, R.string.icon_alt_chinese_flag, 0.80),
    BRITISH_POUND(R.string.currency_british_pound, R.string.currency_code_british_pound, R.drawable.flag_uk, R.string.icon_alt_british_flag, 6.74),
    EURO(R.string.currency_euro, R.string.currency_code_euro, R.drawable.flag_eu, R.string.icon_alt_european_union_flag, 5.72),
    SWISS_FRANC(R.string.currency_swiss_franc, R.string.currency_code_swiss_franc, R.drawable.flag_ch, R.string.icon_alt_swiss_flag, 5.91),
    JAPANESE_YUAN(R.string.currency_japanese_yen, R.string.currency_code_japanese_yen, R.drawable.flag_jp, R.string.icon_alt_japanese_flag, 0.03),
    CANADIAN_DOLLAR(R.string.currency_canadian_dollar, R.string.currency_code_canadian_dollar, R.drawable.flag_ca, R.string.icon_alt_canadian_flag, 4.09),
    BITCOIN(R.string.currency_bitcoin, R.string.currency_code_bitcoin, R.drawable.icon_bitcoin, R.string.icon_alt_bitcoin, 386_491.49),
    DOGECOIN(R.string.currency_dogecoin, R.string.currency_code_dogecoin, R.drawable.icon_dogecoin, R.string.icon_alt_dogecoin, 0.81);

    fun getName(resources: Resources): String { return resources.getString(currencyName) }

    fun getCode(resources: Resources): String { return resources.getString(currencyCode) }

    fun getIconAlt(resources: Resources): String { return resources.getString(currencyIconAlt) }

    companion object {
        fun getCurrencyEnum(resources: Resources, currencyName: String): CurrencyEnum? {
            return CurrencyEnum.entries.firstOrNull { it.getName(resources) == currencyName }
        }
    }

}