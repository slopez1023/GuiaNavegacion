package com.example.guianavegacion.data.model

import androidx.compose.ui.graphics.Color

enum class ProductCategory(
    val displayName: String,
    val imageSymbol: String,
    val badgeColor: Color
) {
    TECNOLOGIA("Tecnología", "💻", Color(0xFF2196F3)),
    ROPA("Ropa", "👕", Color(0xFFE91E63)),
    ALIMENTOS("Alimentos", "🍎", Color(0xFF4CAF50)),
    HOGAR("Hogar", "🏠", Color(0xFFFF9800))
}
