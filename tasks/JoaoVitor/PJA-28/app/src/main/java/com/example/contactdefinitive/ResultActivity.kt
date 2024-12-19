package com.example.contactdefinitive

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.contactdefinitive.databinding.ActivitySecondBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var viewModel: ResultViewModel
    private lateinit var binding: ActivitySecondBinding

    private var coinType1: String = ""
    private var coinType2: String = ""
    private var value: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider( this )[ResultViewModel::class.java]
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getExtra()
        setupListener()
        registerObservers()
        setupView()
    }

    private fun getExtra() {
        val bundle = intent.getBundleExtra("bundle")
        coinType1 = bundle?.getString("coinType1") ?: ""
        coinType2 = bundle?.getString("coinType2") ?: ""
        value = bundle?.getFloat("valor", 0f) ?: 0f
    }

    private fun setupListener() {
        binding.buttonReturn.setOnClickListener {
            finish()
        }
    }

    private fun registerObservers() {
        viewModel.resultLiveData.observe(this) {
            val valueF = "%,.2f".format(it)
            val text = "$valueF $coinType2"
            binding.coinResult.text = text

            binding.coin1.text = "$value $coinType1"
        }

        viewModel.resultErrorLiveData.observe(this) {
            Toast.makeText(this@ResultActivity, it, Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun setupView() {
        with(binding) {
            fun imageView(x : ImageView, y : ImageView) {
                x.visibility = View.VISIBLE
                y.visibility = View.VISIBLE
            }

            when (value != null) {
                (coinType1 == "BRL" && coinType2 == "USD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBR1, imgUSD2)
                }
                (coinType1 == "BRL" && coinType2 == "GBP" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBR1, imgGBP2)
                }
                (coinType1 == "BRL" && coinType2 == "CHF" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBR1, imgCHF2)
                }
                (coinType1 == "BRL" && coinType2 == "EUR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBR1, imgEUR2)
                }
                (coinType1 == "BRL" && coinType2 == "CNY" && value != 0f) -> { //OK
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBR1, imgKWD2)
                }
                (coinType1 == "BRL" && coinType2 == "BHD" && value != 0f) -> { //nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBR1, imgBHD2)
                }
                (coinType1 == "BRL" && coinType2 == "OMR" && value != 0f) -> { //nnnnnnnnnnnnn
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBR1, imgOMR2)
                }
                (coinType1 == "BRL" && coinType2 == "JOD" && value != 0f) -> { //ooooooooooo
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBR1, imgJOD2)
                }
                (coinType1 == "BRL" && coinType2 == "KYD" && value != 0f) -> { //truyeryer65uytu5
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBR1, imgKYD2)
                }




                (coinType1 == "USD" && coinType2 == "BRL" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgUSD1, imgBR2)
                }
                (coinType1 == "USD" && coinType2 == "GBP" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgUSD1, imgGBP2)
                }
                (coinType1 == "USD" && coinType2 == "CHF" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgUSD1, imgCHF2)
                }
                (coinType1 == "USD" && coinType2 == "EUR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgUSD1, imgEUR2)
                }
                (coinType1 == "USD" && coinType2 == "CNY" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgUSD1, imgKWD2)
                }
                (coinType1 == "USD" && coinType2 == "BHD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgUSD1, imgBHD2)
                }
                (coinType1 == "USD" && coinType2 == "OMR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgUSD1, imgOMR2)
                }
                (coinType1 == "USD" && coinType2 == "JOD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgUSD1, imgJOD2)
                }
                (coinType1 == "USD" && coinType2 == "KYD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgUSD1, imgKYD2)
                }




                (coinType1 == "GBP" && coinType2 == "BRL" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgGBP1, imgBR2)
                }
                (coinType1 == "GBP" && coinType2 == "USD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgGBP1, imgUSD2)
                }
                (coinType1 == "GBP" && coinType2 == "CHF" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgGBP1, imgCHF2)
                }
                (coinType1 == "GBP" && coinType2 == "EUR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgGBP1, imgEUR2)
                }
                (coinType1 == "GBP" && coinType2 == "CNY" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgGBP1, imgKWD2)
                }
                (coinType1 == "GBP" && coinType2 == "BHD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgGBP1, imgBHD2)
                }
                (coinType1 == "GBP" && coinType2 == "OMR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgGBP1, imgOMR2)
                }
                (coinType1 == "GBP" && coinType2 == "JOD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgGBP1, imgJOD2)
                }
                (coinType1 == "GBP" && coinType2 == "KYD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgGBP1, imgKYD2)
                }




                (coinType1 == "CHF" && coinType2 == "BRL" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgCHF1, imgBR2)
                }
                (coinType1 == "CHF" && coinType2 == "USD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgCHF1, imgUSD2)
                }
                (coinType1 == "CHF" && coinType2 == "GBP" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgCHF1, imgGBP2)
                }
                (coinType1 == "CHF" && coinType2 == "EUR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgCHF1, imgEUR2)
                }
                (coinType1 == "CHF" && coinType2 == "CNY" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgCHF1, imgKWD2)
                }
                (coinType1 == "CHF" && coinType2 == "BHD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgCHF1, imgBHD2)
                }
                (coinType1 == "CHF" && coinType2 == "OMR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgCHF1, imgOMR2)
                }
                (coinType1 == "CHF" && coinType2 == "JOD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgCHF1, imgJOD2)
                }
                (coinType1 == "CHF" && coinType2 == "KYD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgCHF1, imgKYD2)
                }




                (coinType1 == "EUR" && coinType2 == "BRL" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgEUR1, imgBR2)
                }
                (coinType1 == "EUR" && coinType2 == "USD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgEUR1, imgUSD2)
                }
                (coinType1 == "EUR" && coinType2 == "GBP" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgEUR1, imgGBP2)
                }
                (coinType1 == "EUR" && coinType2 == "CHF" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgEUR1, imgCHF2)
                }
                (coinType1 == "EUR" && coinType2 == "CNY" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgEUR1, imgKWD2)
                }
                (coinType1 == "EUR" && coinType2 == "BHD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgEUR1, imgBHD2)
                }
                (coinType1 == "EUR" && coinType2 == "OMR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgEUR1, imgOMR2)
                }
                (coinType1 == "EUR" && coinType2 == "JOD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgEUR1, imgJOD2)
                }
                (coinType1 == "EUR" && coinType2 == "KYD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgEUR1, imgKYD2)
                }




                (coinType1 == "CNY" && coinType2 == "BRL" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKWD1, imgBR2)
                }
                (coinType1 == "CNY" && coinType2 == "USD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKWD1, imgUSD2)
                }
                (coinType1 == "CNY" && coinType2 == "GBP" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKWD1, imgGBP2)
                }
                (coinType1 == "CNY" && coinType2 == "CHF" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKWD1, imgCHF2)
                }
                (coinType1 == "CNY" && coinType2 == "EUR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKWD1, imgEUR2)
                }
                (coinType1 == "CNY" && coinType2 == "BHD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKWD1, imgBHD2)
                }
                (coinType1 == "CNY" && coinType2 == "OMR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKWD1, imgOMR2)
                }
                (coinType1 == "CNY" && coinType2 == "JOD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKWD1, imgJOD2)
                }
                (coinType1 == "CNY" && coinType2 == "KYD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKWD1, imgKYD2)
                }




                (coinType1 == "BHD" && coinType2 == "BRL" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBHD1, imgBR2)
                }
                (coinType1 == "BHD" && coinType2 == "USD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBHD1, imgUSD2)
                }
                (coinType1 == "BHD" && coinType2 == "GBP" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBHD1, imgGBP2)
                }
                (coinType1 == "BHD" && coinType2 == "CHF" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBHD1, imgCHF2)
                }
                (coinType1 == "BHD" && coinType2 == "CNY" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBHD1, imgKWD2)
                }
                (coinType1 == "BHD" && coinType2 == "EUR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBHD1, imgEUR2)
                }
                (coinType1 == "BHD" && coinType2 == "OMR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBHD1, imgOMR2)
                }
                (coinType1 == "BHD" && coinType2 == "JOD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBHD1, imgJOD2)
                }
                (coinType1 == "BHD" && coinType2 == "KYD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBHD1, imgEUR2)
                }




                (coinType1 == "OMR" && coinType2 == "BRL" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgOMR1, imgBR2)
                }
                (coinType1 == "OMR" && coinType2 == "USD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgOMR1, imgUSD2)
                }
                (coinType1 == "OMR" && coinType2 == "GBP" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgOMR1, imgGBP2)
                }
                (coinType1 == "OMR" && coinType2 == "CHF" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgOMR1, imgCHF2)
                }
                (coinType1 == "OMR" && coinType2 == "CNY" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgOMR1, imgKWD2)
                }
                (coinType1 == "OMR" && coinType2 == "BHD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgOMR1, imgBHD2)
                }
                (coinType1 == "OMR" && coinType2 == "EUR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgOMR1, imgEUR2)
                }
                (coinType1 == "OMR" && coinType2 == "JOD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgOMR1, imgJOD2)
                }
                (coinType1 == "OMR" && coinType2 == "KYD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgOMR1, imgKYD2)
                }




                (coinType1 == "JOD" && coinType2 == "BRL" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgJOD1, imgBR2)
                }
                (coinType1 == "JOD" && coinType2 == "USD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgJOD1, imgUSD2)
                }
                (coinType1 == "JOD" && coinType2 == "GBP" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgJOD1, imgGBP2)
                }
                (coinType1 == "JOD" && coinType2 == "CHF" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgJOD1, imgCHF2)
                }
                (coinType1 == "JOD" && coinType2 == "CNY" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgJOD1, imgKWD2)
                }
                (coinType1 == "JOD" && coinType2 == "BHD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgJOD1, imgBHD2)
                }
                (coinType1 == "JOD" && coinType2 == "OMR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgJOD1, imgOMR2)
                }
                (coinType1 == "JOD" && coinType2 == "EUR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgJOD1, imgEUR2)
                }
                (coinType1 == "JOD" && coinType2 == "KYD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgJOD1, imgKYD2)
                }




                (coinType1 == "KYD" && coinType2 == "BRL" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKYD1, imgBR2)
                }
                (coinType1 == "KYD" && coinType2 == "USD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKYD1, imgUSD2)
                }
                (coinType1 == "KYD" && coinType2 == "GBP" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKYD1, imgGBP2)
                }
                (coinType1 == "KYD" && coinType2 == "CHF" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKYD1, imgCHF2)
                }
                (coinType1 == "KYD" && coinType2 == "CNY" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKYD1, imgKWD2)
                }
                (coinType1 == "KYD" && coinType2 == "BHD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKYD1, imgBHD2)
                }
                (coinType1 == "KYD" && coinType2 == "OMR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKYD1, imgOMR2)
                }
                (coinType1 == "KYD" && coinType2 == "JOD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKYD1, imgJOD2)
                }
                (coinType1 == "KYD" && coinType2 == "EUR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKYD1, imgEUR2)
                }

                else -> {
                    print("error")
                }
            }
        }


        //        with(binding) {
//            fun imageView(x: ImageView, y: ImageView) {
//                x.visibility = View.VISIBLE
//                y.visibility = View.VISIBLE
//            }
//            val coinMap = mapOf(
//                Pair("BRL", "USD") to Pair(imgBR1, imgUSD2),
//                Pair("BRL", "GBP") to Pair(imgBR1, imgGBP2),
//                Pair("BRL", "CHF") to Pair(imgBR1, imgCHF2),
//                Pair("BRL", "EUR") to Pair(imgBR1, imgEUR2),
//                Pair("BRL", "CNY") to Pair(imgBR1, imgKWD2),
//                Pair("BRL", "BHD") to Pair(imgBR1, imgBHD2),
//                Pair("BRL", "OMR") to Pair(imgBR1, imgOMR2),
//                Pair("BRL", "JOD") to Pair(imgBR1, imgJOD2),
//                Pair("BRL", "KYD") to Pair(imgBR1, imgKYD2),
//                Pair("USD", "BRL") to Pair(imgUSD1, imgBR2),
//                Pair("USD", "GBP") to Pair(imgUSD1, imgGBP2),
//                Pair("USD", "CHF") to Pair(imgUSD1, imgCHF2),
//                Pair("USD", "EUR") to Pair(imgUSD1, imgEUR2),
//                Pair("USD", "CNY") to Pair(imgUSD1, imgKWD2),
//                Pair("USD", "BHD") to Pair(imgUSD1, imgBHD2),
//                Pair("USD", "OMR") to Pair(imgUSD1, imgOMR2),
//                Pair("USD", "JOD") to Pair(imgUSD1, imgJOD2),
//                Pair("USD", "KYD") to Pair(imgUSD1, imgKYD2)
//
//            )
//
//            if (value != 0f) {
//                coinMap[Pair(coinType1, coinType2)]?.let { (img1, img2) ->
//                    viewModel.getResult(coinType1, coinType2, value)
//                    imageView(img1, img2)
//                }
//            }else {
//                println("error")
//            }
//        }
//
//
//
//                enum class CurrencyPair {
//                    BRL_USD,
//                    BRL_GBP,
//                    xx,
//                    yy,
//                    zz;
//
//
//                    fun getFlag(): Int {
//                        when (this) {
//                            BRL_USD -> R.drawable.flag_br_svgrepo_com
//                            BRL_GBP -> R.drawable.flag_gb_svgrepo_com
//                            xx -> TODO()
//                            yy -> TODO()
//                            zz -> TODO()
//                        }
//                    }
//
//                    fun from(string: String): CurrencyPair {
//                        when (string) {
//                            "USD" -> BRL_USD
//                        }
//                    }
//                }

//        fun handleCurrencyExchange(coinType1: String, coinType2: String, value: Float) {
//
//            CurrencyPair.from(coinType1).getFlag()
//            if (value == 0f) return
//
//            val pair = CurrencyPair.values().find { it.coinType1 == coinType1 && it.coinType2 == coinType2 }
//
//            pair?.let {
//                viewModel.getResult(it.coinType1, it.coinType2, value)
//                with(binding) {
//                    it.img1.visibility = View.VISIBLE
//                    it.img2.visibility = View.VISIBLE
//                }
//            } ?: run {
//                // Trate caso o par de moedas não seja encontrado
//                Log.e("CurrencyExchange", "Par de moedas inválido: $coinType1 para $coinType2")
//            }
//        }
//        }

    }
}