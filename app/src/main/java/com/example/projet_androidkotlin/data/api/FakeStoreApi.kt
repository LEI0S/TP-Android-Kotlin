package com.example.projet_androidkotlin.data.api
import com.example.projet_androidkotlin.data.model.Cart
import retrofit2.http.GET
import retrofit2.http.Path
import com.example.projet_androidkotlin.data.model.Product
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT

interface FakeStoreApi {
    @GET("products")
    suspend fun getAllProducts(): List<Product>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

    @GET("carts")
    suspend fun getAllCarts(): List<Cart>

    @GET("carts/{id}")
    suspend fun getCartById(@Path("id") id: Int): Cart

    @POST("carts")
    suspend fun createCart(@Body cart: Cart): Cart

    @PUT("carts/{id}")
    suspend fun updateCart(@Path("id") id: Int, @Body cart: Cart): Cart

    @DELETE("carts/{id}")
    suspend fun deleteCart(@Path("id") id: Int)



}