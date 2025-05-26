package com.example.projet_androidkotlin.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.projet_androidkotlin.data.model.Product
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.projet_androidkotlin.viewmodel.ProductViewModel


@Composable
fun ProductScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var filterOption by remember { mutableStateOf("Aucun") }
    val products by viewModel.products.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    // Appliquer recherche + filtre
    val filteredProducts = products
        .filter { it.title.contains(searchQuery, ignoreCase = true) }
        .let {
            when (filterOption) {
                "Prix croissant" -> it.sortedBy { p -> p.price }
                "Prix décroissant" -> it.sortedByDescending { p -> p.price }
                "Alphabétique" -> it.sortedBy { p -> p.title }
                else -> it
            }
        }

    Column(modifier = modifier.padding(16.dp)) {
        // Barre de recherche
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Rechercher un produit") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Menu déroulant des filtres
        var expanded by remember { mutableStateOf(false) }
        Box {
            Button(onClick = { expanded = true }) {
                Text("Filtrer : $filterOption")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listOf("Aucun", "Prix croissant", "Prix décroissant", "Alphabétique").forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            filterOption = option
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Liste filtrée
        LazyColumn {
            items(filteredProducts) { product ->
                ProductItem(product) {
                    navController.navigate("productDetail/${product.id}")
                }
                HorizontalDivider()
            }
        }
    }
}


@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        AsyncImage(
            model = product.image,
            contentDescription = product.title,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(product.title, style = MaterialTheme.typography.titleMedium)
            Text("$${product.price}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
@Composable
fun ProductDetailScreen(productId: String?, viewModel: ProductViewModel = viewModel()) {
    val products by viewModel.products.collectAsState(initial = emptyList())
    val product = products.find { it.id.toString() == productId }

    if (product != null) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = Modifier.fillMaxWidth().height(250.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(product.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Prix : $${product.price}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(product.description ?: "Pas de description", style = MaterialTheme.typography.bodyMedium)
        }
    } else {
        Text("Produit introuvable", modifier = Modifier.padding(16.dp))
    }
}
