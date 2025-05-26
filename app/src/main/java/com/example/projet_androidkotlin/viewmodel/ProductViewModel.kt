// viewmodel/ProductViewModel.kt

package com.example.projet_androidkotlin.viewmodel

import androidx.lifecycle.*
import com.example.projet_androidkotlin.data.api.RetrofitInstance.api
import com.example.projet_androidkotlin.data.model.Cart
import com.example.projet_androidkotlin.data.model.CartItem
import com.example.projet_androidkotlin.data.model.Product
import com.example.projet_androidkotlin.data.model.ProductInCart
import com.example.projet_androidkotlin.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    private val _cart = MutableStateFlow<Cart?>(null)
    val cart: StateFlow<Cart?> = _cart

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

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
    fun createCart(userId: Int, products: List<ProductInCart>) {
        viewModelScope.launch {
            try {
                val newCart = Cart(
                    userId = userId,
                    date = LocalDate.now().toString(),
                    products = products
                )
                val result = api.createCart(newCart)
                _cart.value = result
            } catch (e: Exception) {
                // gestion d'erreur
            }
        }
    }

    fun addProductToCart(product: Product) {

        val currentList = _cartItems.value.toMutableList()
        val existingItemIndex = currentList.indexOfFirst { it.product.id == product.id }
        if (existingItemIndex >= 0) {
            // Si le produit est déjà dans le panier, on augmente la quantité
            val existingItem = currentList[existingItemIndex]
            currentList[existingItemIndex] = existingItem.copy(quantity = existingItem.quantity + 1)
        } else {
            // Sinon on ajoute le produit avec quantité 1
            currentList.add(CartItem(product))
        }
        println("Produit ajouté au panier, total items: ${_cartItems.value.size}")
        _cartItems.value = currentList
    }

    private fun updateCart(cart: Cart) {
        viewModelScope.launch {
            try {
                val result = api.updateCart(cart.id!!, cart)
                _cart.value = result
            } catch (e: Exception) {
                // gestion d'erreur
            }
        }
    }
    fun removeFromCart(product: Product) {
        _cartItems.update { items ->
            val updatedItems = items.toMutableList()
            val item = updatedItems.find { it.product.id == product.id }

            if (item != null) {
                if (item.quantity > 1) {
                    val index = updatedItems.indexOf(item)
                    updatedItems[index] = item.copy(quantity = item.quantity - 1)
                } else {
                    updatedItems.remove(item)
                }
            }

            updatedItems.toList() // ← Force une nouvelle instance pour que collectAsState() détecte le changement
        }
    }
    // Ajoute une unité de plus du produit
    fun increaseQuantity(product: Product) {
        _cartItems.update { items ->
            val updatedItems = items.toMutableList()
            val item = updatedItems.find { it.product.id == product.id }

            if (item != null) {
                val index = updatedItems.indexOf(item)
                updatedItems[index] = item.copy(quantity = item.quantity + 1)
            } else {
                updatedItems.add(CartItem(product))
            }

            updatedItems.toList()
        }
    }

    // Supprime complètement tous les exemplaires du produit
    fun removeProductCompletely(product: Product) {
        _cartItems.update { items ->
            items.filter { it.product.id != product.id }
        }
    }



}
