Projet Android Studio

Jules Paolantoni
Julien Thepaut

Arborescence du code :

```
com.example.projet_androidkotlin
│
├── data/                            # Contient les classes liées aux données de l'application
│   └── model/                       # Définit les modèles de données utilisés dans l'app
│       ├── Product.kt               # Représente un produit avec ses attributs (titre, prix, image, etc.)
│       └── CartItem.kt              # Représente un article dans le panier (produit + quantité)
│
├── ui/                              # Contient tout ce qui est lié à l'affichage (interface utilisateur)
│   └── screens/                     # Écrans de l'application affichés à l'utilisateur
│       ├── ProductScreen.kt         # Affiche la liste des produits disponibles
│       ├── CartScreen.kt            # Affiche les articles ajoutés au panier
│       └── ProductDetailScreen.kt   # Affiche les détails d’un produit sélectionné
│
├── utils/                           # Fonctions utilitaires utilisées dans différentes parties du projet
│   └── generateQRCode.kt            # Génère un QR Code à partir d’un texte (ex: lien ou référence produit)
│
├── viewmodel/                       # Contient les ViewModels pour gérer les données de l'UI
│   └── ProductViewModel.kt          # Gère les états et les données liés aux produits et au panier
```


Documentation disponible dans "Documentation du Projet Android Kotlin.docx"
