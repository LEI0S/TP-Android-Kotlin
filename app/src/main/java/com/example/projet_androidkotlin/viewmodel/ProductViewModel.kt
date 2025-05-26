// viewmodel/ProductViewModel.kt

package com.example.projet_androidkotlin.viewmodel

import androidx.lifecycle.*
import com.example.projet_androidkotlin.data.api.RetrofitInstance.api
import com.example.projet_androidkotlin.data.model.Product
import com.example.projet_androidkotlin.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

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

    fun loadProductById(id: Int) {
        viewModelScope.launch {
            try {
                val fetchedProduct = api.getProductById(id)
                _product.value = fetchedProduct
            } catch (e: Exception) {
                _product.value = null
            }
        }
    }
}
