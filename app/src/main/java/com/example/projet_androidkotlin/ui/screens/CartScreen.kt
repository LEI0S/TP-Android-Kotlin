package com.example.projet_androidkotlin.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
fun CartScreen(
    onBack: () -> Unit,
    navController: NavController,
    viewModel: ProductViewModel = viewModel(),
    onProductClick: (String) -> Unit
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val totalPrice = cartItems.sumOf { it.product.price * it.quantity }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mon Panier") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("products") }) {
                        Icon(Icons.Default.Home, contentDescription = "Accueil")
                    }
                }
            )
        }
    ) { padding ->
        if (cartItems.isEmpty()) {
            Text("Votre panier est vide", modifier = Modifier.padding(16.dp))
        } else {
            Column(modifier = Modifier
                .padding(padding)
                .padding(16.dp)) {
                cartItems.forEach { item ->
                    Row(modifier = Modifier.padding(vertical = 8.dp)) {
                        AsyncImage(
                            model = item.product.image,
                            contentDescription = item.product.title,
                            modifier = Modifier.size(60.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(item.product.title, style = MaterialTheme.typography.titleSmall)
                            Text("Prix unitaire : $${item.product.price}")
                            Text("Quantité : ${item.quantity}")
                            Text("Total : $${"%.2f".format(item.product.price * item.quantity)}")

                            Row {
                                Button(onClick = { viewModel.increaseQuantity(item.product) }) {
                                    Text("+")
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Button(onClick = { viewModel.removeFromCart(item.product) }) {
                                    Text("-")
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Button(onClick = { viewModel.removeProductCompletely(item.product) }) {
                                    Text("Supprimer")
                                }
                            }
                        }
                    }
                    HorizontalDivider()
                }

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Total du panier : $${"%.2f".format(totalPrice)}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
