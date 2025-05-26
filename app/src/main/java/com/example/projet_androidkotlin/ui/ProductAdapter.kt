// ui/ProductAdapter.kt

package com.example.projet_androidkotlin.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projet_androidkotlin.R
import com.example.projet_androidkotlin.data.model.Product

class ProductAdapter(
    private val products: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.productTitle)
        val price = view.findViewById<TextView>(R.id.productPrice)
        val image = view.findViewById<ImageView>(R.id.productImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount() = products.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.title.text = product.title
        holder.price.text = "${product.price} $"
        Glide.with(holder.itemView).load(product.image).into(holder.image)
    }
}
