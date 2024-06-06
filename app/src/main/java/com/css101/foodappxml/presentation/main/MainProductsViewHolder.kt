package com.css101.foodappxml.presentation.main

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.css101.domain.models.Products
import com.css101.foodappxml.R
import com.css101.foodappxml.databinding.ElementCatalogBinding

class MainProductsViewHolder(private val binding: ElementCatalogBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Products): Unit = with(binding) {
        val ctx = tvWeightCatalogElement.context
        tvNameCatalogElement.text = item.name
        tvWeightCatalogElement.text =
            ctx.getString(
                R.string.product_weight,
                item.measure.toInt(),
                item.measureUnit
            )
        tvInCartCatalogElement
        setTags(item)
        setPrice(item)
    }

    private fun setPrice(item: Products) = with(binding) {
        val price = (item.priceCurrent / 100).toInt()
        val ctx = tvWeightCatalogElement.context
        if (item.priceOld == null) {
            val styledText = ctx.getString(R.string.price_regular, price)
            btnAddToCartCatalogElement.text = formatStringHtml(styledText)
        } else {
            val priceOld = (item.priceOld!! / 100).toInt()
            val styledText = ctx.getString(R.string.price_sale, price, priceOld)
            btnAddToCartCatalogElement.text = formatStringHtml(styledText)
        }
    }

    private fun formatStringHtml(string: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            HtmlCompat.fromHtml(string, HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(string)
        }
    }

    private fun setTags(item: Products) = with(binding) {
        ivSpicyCatalogElement.visibility = View.GONE
        ivVeganCatalogElement.visibility = View.GONE
        ivSaleCatalogElement.visibility = View.GONE
        ivNewCatalogElement.visibility = View.GONE
        ivHitCatalogElement.visibility = View.GONE
        item.tagIds.forEach {
            when (it) {
                null -> {}
                0 -> ivSaleCatalogElement.visibility = View.VISIBLE
                1 -> ivNewCatalogElement.visibility = View.VISIBLE
                2 -> ivVeganCatalogElement.visibility = View.VISIBLE
                3 -> ivHitCatalogElement.visibility = View.VISIBLE
                4 -> ivSpicyCatalogElement.visibility = View.VISIBLE
            }
        }
    }
}