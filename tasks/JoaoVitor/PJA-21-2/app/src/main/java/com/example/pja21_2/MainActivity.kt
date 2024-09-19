package com.example.pja21_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pja21_2.ui.theme.PJA212Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("debug", "onCreate: Acionado assim que o sistema cria a atividade")
    }

    override fun onStart() {
        super.onStart()
        Log.i("debug", "onStart: Quando a atividade entra no estado Iniciado")
    }

    override fun onResume() {
        super.onResume()
        Log.i("debug", "onResume: Quando a atividade entra no estado Retomado")
    }

    override fun onPause() {
        super.onPause()
        Log.i("debug", "onPause: Quando ocorre um evento de interrupção")
    }

    override fun onStop() {
        super.onStop()
        Log.i("debug", "onStop: Quando a atividade não está mais visível para o usuário")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("debug", "onDestroy: Quando a atividade é destruida")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("debug", "onRestart: Quando a atividade retorna do onStop() e retona ao onStart")
    }
}

