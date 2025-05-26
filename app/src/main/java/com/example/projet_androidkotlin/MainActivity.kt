package com.example.projet_androidkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projet_androidkotlin.ui.screens.ProductDetailScreen
import com.example.projet_androidkotlin.ui.screens.ProductScreen
import com.example.projet_androidkotlin.ui.theme.Projet_AndroidKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Projet_AndroidKotlinTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "products") {
                    composable("products") {
                        ProductScreen(navController)
                    }
                    composable("productDetail/{productId}") { backStackEntry ->
                        val productId = backStackEntry.arguments?.getString("productId")
                        ProductDetailScreen(productId)
                    }
                }
            }
        }


    }
}
