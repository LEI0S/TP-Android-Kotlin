package com.example.projet_androidkotlin.data.model

data class CartItem(
    val product: Product,
    var quantity: Int = 1
)
