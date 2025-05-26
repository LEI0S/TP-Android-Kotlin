// viewmodel/ProductViewModel.kt

package com.example.projet_androidkotlin.viewmodel

import androidx.lifecycle.*
import com.example.projet_androidkotlin.data.model.Product
import com.example.projet_androidkotlin.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products


    fun loadProducts() {
        viewModelScope.launch {
            try {
                val result = repository.getAllProducts()
                _products.value = result
            } catch (e: Exception) {
                // Gestion d’erreur si besoin
            }
        }
    }
}
