package com.example.guianavegacion.ui.register

import androidx.lifecycle.ViewModel
import com.example.guianavegacion.data.model.Product
import com.example.guianavegacion.data.model.ProductCategory
import com.example.guianavegacion.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class RegisterUiState(
    val name: String = "",
    val price: String = "",
    val selectedCategory: ProductCategory = ProductCategory.TECNOLOGIA,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

class RegisterProductViewModel(
    private val repository: ProductRepository = ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNameChange(newName: String) {
        _uiState.value = _uiState.value.copy(name = newName, errorMessage = null, isSuccess = false)
    }

    fun onPriceChange(newPrice: String) {
        _uiState.value = _uiState.value.copy(price = newPrice, errorMessage = null, isSuccess = false)
    }

    fun onCategoryChange(newCategory: ProductCategory) {
        _uiState.value = _uiState.value.copy(selectedCategory = newCategory, errorMessage = null, isSuccess = false)
    }

    fun registerProduct(): Boolean {
        val currentState = _uiState.value
        val nameTrimmed = currentState.name.trim()
        val priceDouble = currentState.price.toDoubleOrNull()

        if (nameTrimmed.isEmpty()) {
            _uiState.value = currentState.copy(errorMessage = "El nombre del producto no puede estar vacío.")
            return false
        }

        if (priceDouble == null || priceDouble <= 0) {
            _uiState.value = currentState.copy(errorMessage = "Ingrese un precio válido mayor a 0.")
            return false
        }

        val newProduct = Product(
            name = nameTrimmed,
            price = priceDouble,
            category = currentState.selectedCategory
        )

        repository.addProduct(newProduct)

        // Limpiar campos y marcar éxito
        _uiState.value = RegisterUiState(isSuccess = true)
        return true
    }

    fun resetSuccessFlag() {
        _uiState.value = _uiState.value.copy(isSuccess = false)
    }
}
