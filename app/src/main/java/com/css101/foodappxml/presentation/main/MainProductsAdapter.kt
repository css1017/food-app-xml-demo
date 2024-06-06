package com.css101.foodappxml.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.css101.domain.models.Products
import com.css101.foodappxml.databinding.ElementCatalogBinding

class MainProductsAdapter(
    private val items: List<Products>,
    private var onItemClick: ((Products) -> Unit),
) : RecyclerView.Adapter<MainProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ElementCatalogBinding.inflate(inflater, parent, false)
        return MainProductsViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MainProductsViewHolder, position: Int) {
        holder.bind(items[position])
        val product = items[position]
        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }
}