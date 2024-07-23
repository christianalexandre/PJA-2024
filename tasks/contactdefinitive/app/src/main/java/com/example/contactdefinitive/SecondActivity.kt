package com.example.contactdefinitive

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contactdefinitive.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListener1()
    }
    fun closeActivity() {
        finish()
    }
    private fun setupListener1() {
        val bundle = intent.getBundleExtra("bundle")
        val coinValue1 = bundle?.getString("coinType1")
        val coinValue2 = bundle?.getString("coinType2")
        val valor: Float? = bundle?.getFloat("valor", 0f)

        val bandeiraBR1 = findViewById<ImageView>(R.id.imgBR1)
        val bandeiraUSD1 = findViewById<ImageView>(R.id.imgUSD1)
        val bandeiraGBP1 = findViewById<ImageView>(R.id.imgGBP1)
        val bandeiraCHF1 = findViewById<ImageView>(R.id.imgCHF1)
        val bandeiraEUR1 = findViewById<ImageView>(R.id.imgEUR1)

        val bandeiraBR2 = findViewById<ImageView>(R.id.imgBR2)
        val bandeiraUSD2 = findViewById<ImageView>(R.id.imgUSD2)
        val bandeiraGBP2 = findViewById<ImageView>(R.id.imgGBP2)
        val bandeiraCHF2 = findViewById<ImageView>(R.id.imgCHF2)
        val bandeiraEUR2 = findViewById<ImageView>(R.id.imgEUR2)

        var coin1 = findViewById<TextView>(R.id.coin1)
        var coinResult = findViewById<TextView>(R.id.coinResult)

        if (valor == 0.0f) {
            Toast.makeText(this, "Favor colocar um valor!", Toast.LENGTH_SHORT).show()
            closeActivity()
        }else if (coinValue1 == "BRL" && coinValue2 == "USD" && valor != null) {
            val resultado = (valor / 5.30f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraBR1.visibility = View.VISIBLE
            bandeiraUSD2.visibility = View.VISIBLE
        } else if (coinValue1 == "BRL" && coinValue2 == "GBP" && valor != null) {
            val resultado = (valor / 6.74f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraBR1.visibility = View.VISIBLE
            bandeiraGBP2.visibility = View.VISIBLE
        } else if (coinValue1 == "BRL" && coinValue2 == "CHF" && valor != null) {
            val resultado = (valor / 5.91f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraBR1.visibility = View.VISIBLE
            bandeiraCHF2.visibility = View.VISIBLE
        } else if (coinValue1 == "BRL" && coinValue2 == "EUR" && valor != null) {
            val resultado = (valor / 5.72f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraBR1.visibility = View.VISIBLE
            bandeiraEUR2.visibility = View.VISIBLE
        } else if (coinValue1 == "USD" && coinValue2 == "BRL" && valor != null) {
            val resultado = (valor * 5.3f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraUSD1.visibility = View.VISIBLE
            bandeiraBR2.visibility = View.VISIBLE
        } else if (coinValue1 == "USD" && coinValue2 == "GBP" && valor != null) {
            val resultado = (valor / 1.44f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraUSD1.visibility = View.VISIBLE
            bandeiraGBP2.visibility = View.VISIBLE
        } else if (coinValue1 == "USD" && coinValue2 == "CHF" && valor != null) {
            val resultado = (valor / 0.61f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraUSD1.visibility = View.VISIBLE
            bandeiraCHF2.visibility = View.VISIBLE
        } else if (coinValue1 == "USD" && coinValue2 == "EUR" && valor != null) {
            val resultado = (valor / 0.42f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraUSD1.visibility = View.VISIBLE
            bandeiraEUR2.visibility = View.VISIBLE
        } else if (coinValue1 == "GBP" && coinValue2 == "BRL" && valor != null) {
            val resultado = (valor * 6.74f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraGBP1.visibility = View.VISIBLE
            bandeiraBR2.visibility = View.VISIBLE
        } else if (coinValue1 == "GBP" && coinValue2 == "USD" && valor != null) {
            val resultado = (valor * 1.44f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraGBP1.visibility = View.VISIBLE
            bandeiraUSD2.visibility = View.VISIBLE
        } else if (coinValue1 == "GBP" && coinValue2 == "CHF" && valor != null) {
            val resultado = (valor * 0.83f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraGBP1.visibility = View.VISIBLE
            bandeiraCHF2.visibility = View.VISIBLE
        } else if (coinValue1 == "GBP" && coinValue2 == "EUR" && valor != null) {
            val resultado = (valor * 1.02f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraGBP1.visibility = View.VISIBLE
            bandeiraEUR2.visibility = View.VISIBLE
        } else if (coinValue1 == "CHF" && coinValue2 == "BRL" && valor != null) {
            val resultado = (valor * 5.91f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraCHF1.visibility = View.VISIBLE
            bandeiraBR2.visibility = View.VISIBLE
        } else if (coinValue1 == "CHF" && coinValue2 == "USD" && valor != null) {
            val resultado = (valor * 0.61f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraCHF1.visibility = View.VISIBLE
            bandeiraUSD2.visibility = View.VISIBLE
        } else if (coinValue1 == "CHF" && coinValue2 == "GBP" && valor != null) {
            val resultado = (valor / 0.83f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraCHF1.visibility = View.VISIBLE
            bandeiraGBP2.visibility = View.VISIBLE
        } else if (coinValue1 == "CHF" && coinValue2 == "EUR" && valor != null) {
            val resultado = (valor * 0.19f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraCHF1.visibility = View.VISIBLE
            bandeiraEUR2.visibility = View.VISIBLE
        } else if (coinValue1 == "EUR" && coinValue2 == "BRL" && valor != null) {
            val resultado = (valor * 5.72f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraEUR1.visibility = View.VISIBLE
            bandeiraBR2.visibility = View.VISIBLE
        } else if (coinValue1 == "EUR" && coinValue2 == "USD" && valor != null) {
            val resultado = (valor * 0.42f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraEUR1.visibility = View.VISIBLE
            bandeiraUSD2.visibility = View.VISIBLE
        } else if (coinValue1 == "EUR" && coinValue2 == "GBP" && valor != null) {
            val resultado = (valor / 1.02f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraEUR1.visibility = View.VISIBLE
            bandeiraGBP2.visibility = View.VISIBLE
        } else if (coinValue1 == "EUR" && coinValue2 == "CHF" && valor != null) {
            val resultado = (valor / 0.19f)
            coin1.text = "$valor $coinValue1"
            val resultValue = String.format("%.2f", resultado)
            val completeValue = "$resultValue $coinValue2"
            coinResult.text = completeValue
            bandeiraEUR1.visibility = View.VISIBLE
            bandeiraCHF2.visibility = View.VISIBLE
        }else {
            Toast.makeText(this, "Favor colocar moedas distintas!", Toast.LENGTH_SHORT).show()
            closeActivity()
        }



//        } else if (coinValue1 == "EUR" && coinValue2 == "EUR" && valor != null) {
//            val resultado = (valor)
//            coin1.text = "$valor $coinValue1"
//            val resultValue = String.format("%.2f", resultado)
//            val completeValue = "$resultValue $coinValue2"
//            coinResult.text = completeValue
//            bandeiraEUR1.visibility = View.VISIBLE
//            bandeiraEUR2.visibility = View.VISIBLE
//        } else if (coinValue1 == "CHF" && coinValue2 == "CHF" && valor != null) {
//            val resultado = (valor)
//            coin1.text = "$valor $coinValue1"
//            val resultValue = String.format("%.2f", resultado)
//            val completeValue = "$resultValue $coinValue2"
//            coinResult.text = completeValue
//            bandeiraCHF1.visibility = View.VISIBLE
//            bandeiraCHF2.visibility = View.VISIBLE
//        } else if (coinValue1 == "GBP" && coinValue2 == "GBP" && valor != null) {
//            val resultado = (valor)
//            coin1.text = "$valor $coinValue1"
//            val resultValue = String.format("%.2f", resultado)
//            val completeValue = "$resultValue $coinValue2"
//            coinResult.text = completeValue
//            bandeiraGBP1.visibility = View.VISIBLE
//            bandeiraGBP2.visibility = View.VISIBLE
//        } else if (coinValue1 == "USD" && coinValue2 == "USD" && valor != null) {
//            val resultado = (valor)
//            coin1.text = "$valor $coinValue1"
//            val resultValue = String.format("%.2f", resultado)
//            val completeValue = "$resultValue $coinValue2"
//            coinResult.text = completeValue
//            bandeiraUSD1.visibility = View.VISIBLE
//            bandeiraUSD2.visibility = View.VISIBLE
//        } else if (coinValue1 == "BRL" && coinValue2 == "BRL" && valor != null) {
//            val resultado = (valor)
//            coin1.text = "$valor $coinValue1"
//            val resultValue = String.format("%.2f", resultado)
//            val completeValue = "$resultValue $coinValue2"
//            coinResult.text = completeValue
//            bandeiraBR1.visibility = View.VISIBLE
//            bandeiraBR2.visibility = View.VISIBLE
//        }

        val buttonReturn = findViewById<Button>(R.id.buttonRetornar)

        fun closeActivity() {
            finish()
        }

        buttonReturn.setOnClickListener {
            closeActivity()
        }
    }
}