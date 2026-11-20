package com.example.guianavegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.guianavegacion.ui.register.RegisterProductScreen
import com.example.guianavegacion.ui.theme.GuiaNavegacionTheme

class RegisterProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GuiaNavegacionTheme {
                RegisterProductScreen(
                    onBackClick = { finish() }
                )
            }
        }
    }
}
