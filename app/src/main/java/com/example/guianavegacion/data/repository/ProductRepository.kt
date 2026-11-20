package com.example.guianavegacion.data.repository

import com.example.guianavegacion.data.model.Product
import com.example.guianavegacion.data.model.ProductCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object ProductRepository {
    private val _products = MutableStateFlow<List<Product>>(
        listOf(
            Product(name = "Smartphone Pro", price = 899.99, category = ProductCategory.TECNOLOGIA),
            Product(name = "Camiseta de Algodón", price = 25.50, category = ProductCategory.ROPA)
        )
    )
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    fun addProduct(product: Product) {
        _products.value = _products.value + product
    }
}
