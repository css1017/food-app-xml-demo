package com.css101.foodappxml.presentation.main

import android.annotation.SuppressLint
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.css101.domain.models.Categories
import com.css101.foodappxml.R
import com.css101.foodappxml.databinding.ElementCategoryCatalogBinding

class MainCategoriesAdapter(
    private val items: List<Categories>,
    private var selected: Int?,
    private var onItemClick: ((Categories, LinearLayoutCompat) -> Unit),
) : RecyclerView.Adapter<MainCategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ElementCategoryCatalogBinding.inflate(inflater, parent, false)
        return MainCategoriesViewHolder(binding)
    }

    override fun getItemCount() = items.size

    @Suppress("DEPRECATION")
    override fun onBindViewHolder(holder: MainCategoriesViewHolder, position: Int) {
        holder.bind(items[position])
        val category = items[position]
        val btn = holder.btn
        val ctx = btn.context
        if (selected != null && selected == category.id) {
            btn.setBackgroundDrawable(
                ResourcesCompat.getDrawable(
                    ctx.resources,
                    R.drawable.bg_button_primary, null
                )
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                btn.setTextColor(ctx.resources.getColor(R.color.white, null))
            } else btn.setTextColor(ctx.resources.getColor(R.color.white))
        } else {
            btn.background = null
            val typedValue = TypedValue()
            ctx.theme.resolveAttribute(R.attr.wb, typedValue, true)
            val color = typedValue.data
            btn.setTextColor(color)
        }
        btn.setOnClickListener {
            onItemClick(category, holder.ll)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSelectedCategory(selectedId: Int?) {
        this.selected = selectedId
        notifyDataSetChanged()
    }
}