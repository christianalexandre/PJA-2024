package com.example.contactdefinitive

import android.os.Bundle
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
            val text = "$it $coinType1"
            binding.coinResult.text = text

            binding.coin1.text = "$value $coinType1"
        }

        viewModel.resultErrorLiveData.observe(this) {
            Toast.makeText(this@ResultActivity, it, Toast.LENGTH_LONG).show()
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
                (coinType1 == "BRL" && coinType2 == "KWD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBR1, imgKWD2)
                }
                (coinType1 == "BRL" && coinType2 == "BHD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBR1, imgBHD2)
                }
                (coinType1 == "BRL" && coinType2 == "OMR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBR1, imgOMR2)
                }
                (coinType1 == "BRL" && coinType2 == "JOD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgBR1, imgJOD2)
                }
                (coinType1 == "BRL" && coinType2 == "KYD" && value != 0f) -> {
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
                (coinType1 == "USD" && coinType2 == "KWD" && value != 0f) -> {
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
                (coinType1 == "GBP" && coinType2 == "KWD" && value != 0f) -> {
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
                (coinType1 == "CHF" && coinType2 == "KWD" && value != 0f) -> {
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
                (coinType1 == "EUR" && coinType2 == "KWD" && value != 0f) -> {
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




                (coinType1 == "KWD" && coinType2 == "BRL" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKWD1, imgBR2)
                }
                (coinType1 == "KWD" && coinType2 == "USD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKWD1, imgUSD2)
                }
                (coinType1 == "KWD" && coinType2 == "GBP" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKWD1, imgGBP2)
                }
                (coinType1 == "KWD" && coinType2 == "CHF" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKWD1, imgCHF2)
                }
                (coinType1 == "KWD" && coinType2 == "EUR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKWD1, imgEUR2)
                }
                (coinType1 == "KWD" && coinType2 == "BHD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKWD1, imgBHD2)
                }
                (coinType1 == "KWD" && coinType2 == "OMR" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKWD1, imgOMR2)
                }
                (coinType1 == "KWD" && coinType2 == "JOD" && value != 0f) -> {
                    viewModel.getResult(coinType1, coinType2, value)
                    imageView(imgKWD1, imgJOD2)
                }
                (coinType1 == "KWD" && coinType2 == "KYD" && value != 0f) -> {
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
                (coinType1 == "BHD" && coinType2 == "KWD" && value != 0f) -> {
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
                (coinType1 == "OMR" && coinType2 == "KWD" && value != 0f) -> {
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
                (coinType1 == "JOD" && coinType2 == "KWD" && value != 0f) -> {
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
                (coinType1 == "KYD" && coinType2 == "KWD" && value != 0f) -> {
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
    }
}