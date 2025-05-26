package com.example.projet_androidkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projet_androidkotlin.ui.screens.CartScreen
import com.example.projet_androidkotlin.ui.screens.ProductDetailScreen
import com.example.projet_androidkotlin.ui.screens.ProductScreen
import com.example.projet_androidkotlin.ui.theme.Projet_AndroidKotlinTheme
import com.example.projet_androidkotlin.viewmodel.ProductViewModel // ✅ Assure-toi d'importer ta classe ici

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Projet_AndroidKotlinTheme {
                val navController = rememberNavController()

                // ✅ Instanciation correcte du ViewModel à partir de l'activité
                val viewModel: ProductViewModel = viewModel()

                NavHost(navController, startDestination = "products") {
                    composable("products") {
                        ProductScreen(
                            navController = navController,
                            viewModel = viewModel,
                            onCartClick = { navController.navigate("cart") }
                        )
                    }

                    composable("productDetail/{productId}") { backStackEntry ->
                        val productId = backStackEntry.arguments?.getString("productId")
                        ProductDetailScreen(
                            productId = productId,
                            navController = navController,
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() },
                            onGoToCart = { navController.navigate("cart") },
                            onCartClick = { navController.navigate("cart") }

                        )
                    }

                    composable("cart") {
                        CartScreen(
                            onBack = { navController.popBackStack() },
                            viewModel = viewModel,
                            onProductClick = { productId ->
                                navController.navigate("productDetail/$productId")
                            },
                            navController = navController
                        )
                    }

                }
            }
        }
    }
}
