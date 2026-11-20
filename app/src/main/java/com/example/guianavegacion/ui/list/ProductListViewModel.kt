package com.example.guianavegacion.ui.list

import androidx.lifecycle.ViewModel
import com.example.guianavegacion.data.model.Product
import com.example.guianavegacion.data.repository.ProductRepository
import kotlinx.coroutines.flow.StateFlow

class ProductListViewModel(
    repository: ProductRepository = ProductRepository
) : ViewModel() {
    val products: StateFlow<List<Product>> = repository.products
}
