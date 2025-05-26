package com.example.projet_androidkotlin.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
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
import coil.compose.AsyncImage
import com.example.projet_androidkotlin.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBack: () -> Unit,
    viewModel: ProductViewModel = viewModel(),
    onProductClick: (String) -> Unit // 👈 Ajoute ce paramètre pour naviguer
) {
    val cartItems by viewModel.cartItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mon Panier") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
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
                            Text("Prix : $${item.product.price}", style = MaterialTheme.typography.bodyMedium)
                            Text("Quantité : ${item.quantity}", style = MaterialTheme.typography.bodySmall)

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
            }
        }
    }
}
