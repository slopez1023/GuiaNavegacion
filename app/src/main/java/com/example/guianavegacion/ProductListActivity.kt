package com.example.guianavegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.guianavegacion.ui.list.ProductListScreen
import com.example.guianavegacion.ui.theme.GuiaNavegacionTheme

class ProductListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GuiaNavegacionTheme {
                ProductListScreen(
                    onBackClick = { finish() }
                )
            }
        }
    }
}
