package com.example.projet_androidkotlin.data.repository
import com.example.projet_androidkotlin.data.api.RetrofitInstance
import com.example.projet_androidkotlin.data.model.Product

class ProductRepository {

    suspend fun getAllProducts(): List<Product> {
        return RetrofitInstance.api.getAllProducts()
    }

    suspend fun getProductById(id: Int): Product {
        return RetrofitInstance.api.getProductById(id)
    }
}