📱 Documentation du Projet Android Kotlin - Application de Produits avec Panier et QR Code
🧾 Sommaire
Présentation générale

Architecture de l’application

Fonctionnalités principales

Structure des packages

Détails des écrans

VueModèle (ViewModel)

QR Code – Génération & Utilisation

Navigation

Améliorations possibles

Dépendances importantes

📌 Présentation générale
L'application permet :

d’afficher une liste de produits,

de consulter les détails de chaque produit,

d’ajouter des produits au panier,

de modifier les quantités,

de générer un QR code menant à la page de détail d’un produit.

Technologies utilisées :

Kotlin

Jetpack Compose (Material 3)

Navigation Compose

Coil pour le chargement d’images

ZXing pour les QR codes

🏛️ Architecture de l’application
Architecture MVVM :

Model → objets Product, CartItem

ViewModel → logique de gestion des produits et du panier

View → Composables (ProductScreen, CartScreen, etc.)

✨ Fonctionnalités principales
Fonction	Description
📋 Liste de produits	Affichage des produits avec filtre et recherche
🔍 Détail produit	Image, prix, description, bouton "Ajouter au panier"
🛒 Panier	Ajout/suppression/quantité, total dynamique
📷 QR Code	Généré pour chaque produit pour le scanner ultérieurement
🧭 Navigation	Entre écran d’accueil, panier, détail produit

📁 Structure des packages
kotlin
Copier
Modifier
com.example.projet_androidkotlin
│
├── data/
│   └── model/ → Product.kt, CartItem.kt
│
├── ui/
│   └── screens/ → ProductScreen.kt, CartScreen.kt, ProductDetailScreen.kt
│
├── utils/ → generateQRCode.kt
│
├── viewmodel/ → ProductViewModel.kt
🖼️ Détails des écrans
ProductScreen.kt
Affiche tous les produits dans une LazyColumn

Barre de recherche

Menu de filtrage : prix croissant, décroissant, ordre alphabétique

Boutons : accueil, panier

ProductDetailScreen.kt
Détail du produit (image, titre, prix, description)

Bouton “Ajouter au panier”

Boutons d’accès rapide à l’accueil et au panier

Intégration du QR code possible via ProductQRCode()

CartScreen.kt
Liste des produits dans le panier

Boutons :

+ : augmente la quantité

- : diminue la quantité

Supprimer : retire complètement

Total affiché automatiquement

Navigation : retour & accueil

🧠 VueModèle (ProductViewModel.kt)
Contient :

products: liste complète chargée (flow)

cartItems: état du panier (flow)

product: produit sélectionné pour détail

Fonctions principales :

kotlin
Copier
Modifier
fun loadProducts()
fun loadProductById(id: Int)
fun addProductToCart(product: Product)
fun increaseQuantity(product: Product)
fun removeFromCart(product: Product)
fun removeProductCompletely(product: Product)
🔳 QR Code – Génération & Utilisation
Fichier : generateQRCode.kt
Fonction pour créer un Bitmap à partir d'une chaîne (URL, ID produit, etc.)

kotlin
Copier
Modifier
fun generateQRCode(content: String): Bitmap
Utilisation dans un Composable :

kotlin
Copier
Modifier
@Composable
fun ProductQRCode(productUrl: String) {
    val qrBitmap = remember(productUrl) { generateQRCode(productUrl) }
    Image(bitmap = qrBitmap.asImageBitmap(), contentDescription = null)
}
👉 Tu peux appeler cette composable dans ProductDetailScreen.

🧭 Navigation
Définie avec NavHost :

kotlin
Copier
Modifier
NavHost(navController, startDestination = "products") {
    composable("products") { ProductScreen(...) }
    composable("cart") { CartScreen(...) }
    composable("productDetail/{productId}") { backStackEntry ->
        val productId = backStackEntry.arguments?.getString("productId")
        ProductDetailScreen(productId = productId, ...)
    }
}
🚀 Améliorations possibles
✅ Ajout d’un écran de confirmation de commande

✅ Implémenter persistance locale avec Room ou DataStore

✅ Design responsive (tablette/paysage)

✅ Ajout d’une animation lors de l’ajout au panier

✅ Intégrer un scanner de QR code pour lire les QR créés

✅ Authentification utilisateur (Firebase ?)

📦 Dépendances importantes
kotlin
Copier
Modifier
// Compose
implementation("androidx.compose.material3:material3:1.2.0")
implementation("androidx.navigation:navigation-compose:2.6.0")

// Coil pour les images
implementation("io.coil-kt:coil-compose:2.3.0")

// ZXing pour QR code
implementation("com.google.zxing:core:3.5.2")

// Lifecycle & ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
