package com.example.projet_androidkotlin.data.api
import retrofit2.http.GET
import retrofit2.http.Path
import com.example.projet_androidkotlin.data.model.Product
interface FakeStoreApi {
    @GET("products")
    suspend fun getAllProducts(): List<Product>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product
}