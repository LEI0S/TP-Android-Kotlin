package com.example.projet_androidkotlin.data.model

data class Cart(
    val id: Int? = null,
    val userId: Int,
    val date: String,
    val products: List<ProductInCart>
)

data class ProductInCart(
    val productId: Int,
    val quantity: Int
)

