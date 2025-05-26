package com.example.projet_androidkotlin.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(navController: NavController,
                  viewModel: ProductViewModel = viewModel(),
                  onCartClick: () -> Unit) {

    var searchQuery by remember { mutableStateOf("") }
    var filterOption by remember { mutableStateOf("Aucun") }
    val products by viewModel.products.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Produits") },
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
    ) { padding ->

        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Rechercher un produit") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

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

            LazyColumn {
                items(filteredProducts) { product ->
                    ProductItem(product = product, onClick = {
                        navController.navigate("productDetail/${product.id}")
                    })
                    HorizontalDivider()
                }
            }
        }
    }
}



@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
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




