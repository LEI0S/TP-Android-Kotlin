// ui/ProductListActivity.kt

package com.example.projet_androidkotlin.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projet_androidkotlin.databinding.ActivityProductListBinding
import com.example.projet_androidkotlin.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

class ProductListActivity : ComponentActivity() {

    private lateinit var binding: ActivityProductListBinding
    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                productViewModel.products.collect { productList ->
                    binding.recyclerViewProducts.adapter = ProductAdapter(productList)
                }
            }
        }

        productViewModel.loadProducts()
    }
}
