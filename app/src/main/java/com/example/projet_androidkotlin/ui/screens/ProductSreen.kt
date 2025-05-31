package com.example.projet_androidkotlin.ui.screens

import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.SuperscriptSpan
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projet_androidkotlin.data.model.Product
import com.example.projet_androidkotlin.utils.generateQRCode
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
                        Icon(Icons.Default.Home, contentDescription = "Accueil", tint = Color(0xFF850606))
                    }
                    IconButton(onClick = onCartClick) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Panier", tint=Color(0xFF850606))
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
                    .padding(bottom = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF850606),
                    unfocusedBorderColor = Color(0xFF850606),
                    cursorColor = Color(0xFF850606))
            )

            var expanded by remember { mutableStateOf(false) }
            Box {
                Button(onClick = { expanded = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF850606) // Rouge écarlate
                    )
                    ) {
                    Text("Filtrer : $filterOption", color = Color.White)
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
fun setFormattedPrice(textView: TextView, price: Double) {
    val dollars = price.toInt()
    val cents = ((price - dollars) * 100).toInt()

    val fullText = "$$dollars${String.format("%02d", cents)}"
    val spannable = SpannableString(fullText)

    // $ symbole plus petit
    spannable.setSpan(RelativeSizeSpan(0.6f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    // Chiffre principal (ex: 2) taille normale
    spannable.setSpan(RelativeSizeSpan(1.4f), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    // Centimes plus petits et en exposant
    spannable.setSpan(RelativeSizeSpan(0.7f), 2, fullText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannable.setSpan(SuperscriptSpan(), 2, fullText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    textView.text = spannable
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(product.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text("$${product.price}", style = MaterialTheme.typography.bodyMedium)
            }

            // QR Code
            val qrBitmap = remember(product.id) {
                generateQRCode("productDetail/${product.id}")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                bitmap = qrBitmap.asImageBitmap(),
                contentDescription = "QR Code",
                modifier = Modifier.size(60.dp)
            )
        }
    }
}







