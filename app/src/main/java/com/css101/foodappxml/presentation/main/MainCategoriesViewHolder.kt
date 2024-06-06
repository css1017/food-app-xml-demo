package com.css101.foodappxml.presentation.main

import androidx.recyclerview.widget.RecyclerView
import com.css101.domain.models.Categories
import com.css101.foodappxml.databinding.ElementCategoryCatalogBinding

class MainCategoriesViewHolder(private val binding: ElementCategoryCatalogBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val btn = binding.btnCategoryCatalogElement
    val ll = binding.llCategoryCatalogElement
    fun bind(item: Categories): Unit = with(binding) {
        btnCategoryCatalogElement.setBackgroundDrawable(null)
        btnCategoryCatalogElement.text = item.name
    }
}


