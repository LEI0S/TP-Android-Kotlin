package com.example.projet_androidkotlin.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projet_androidkotlin.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: String?,
    navController: NavController,
    viewModel: ProductViewModel = viewModel(),
    onBack: () -> Unit,
    onGoToCart: () -> Unit,
    onCartClick: () -> Unit
) {
    val product by viewModel.product.collectAsState()

    LaunchedEffect(productId) {
        productId?.toIntOrNull()?.let { viewModel.loadProductById(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détail produit") },
                actions = {
                    IconButton(onClick = { navController.navigate("products") }) {
                        Icon(Icons.Default.Home, contentDescription = "Accueil")
                    }
                    IconButton(onClick = onCartClick) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Panier")
                    }
                }
            )
        }
    )  { padding ->
        if (product != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = product!!.image,
                    contentDescription = product!!.title,
                    modifier = Modifier.fillMaxWidth().height(250.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(product!!.title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Prix : $${product!!.price}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(product!!.description ?: "Pas de description")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    product?.let { viewModel.addProductToCart(it) }
                    onGoToCart()
                }) {
                    Text("Ajouter au panier")
                }

            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text("Produit introuvable", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
