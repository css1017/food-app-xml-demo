package com.css101.foodappxml.presentation.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.css101.domain.models.Categories
import com.css101.domain.models.Products
import com.css101.foodappxml.R
import com.css101.foodappxml.databinding.ActivityMainBinding
import com.css101.foodappxml.presentation.details.ProductFragment
import com.css101.foodappxml.presentation.filter.FilterFragment
import com.css101.foodappxml.presentation.filter.FilterType
import com.css101.foodappxml.presentation.search.SearchFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val vm by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //todo remove
        vm.getProducts()
        vm.getCategories()
        setTheme(R.style.Theme_FoodiesXmlDemo)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading()
        setButtons()
        vm.categories.observe(this@MainActivity) {
            setCategoriesAdapter(it, vm.selectedCategoryLive.value)
        }
        vm.products.observe(this@MainActivity) { list ->
            setContentAdapter(list)
        }
        binding.tvCartCatalog.text = getString(R.string.empty_in_cart)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setCategoriesAdapter(categories: List<Categories>, selectedId: Int?) {
        vm.categoriesLayoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        vm.categoriesAdapter = MainCategoriesAdapter(categories, selectedId) { category, view ->
            handleCategorySelection(category, view, categories)
        }
        binding.rvCategoriesCatalog.apply {
            layoutManager = vm.categoriesLayoutManager
            adapter = vm.categoriesAdapter
        }
        binding.rvCategoriesCatalog.adapter?.notifyDataSetChanged()
    }

    private fun handleCategorySelection(
        category: Categories,
        view: View,
        categories: List<Categories>
    ) {
        val position = categories.indexOf(category)
        val offset = (binding.rvCategoriesCatalog.width / 2) - (view.measuredWidth / 2)
        vm.categoriesLayoutManager.scrollToPositionWithOffset(position, offset)
        vm.categoriesAdapter.updateSelectedCategory(category.id)
        lifecycleScope.launch(Dispatchers.Default) {
            vm.saveSelectedCategory(category.id)
            vm.filterByCategory(category.id)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setContentAdapter(products: List<Products>) {
        if (products.isEmpty()) {
            showEmpty()
        } else showContent()
        val adapter = MainProductsAdapter(products) {
            val fragment = ProductFragment.newInstance(it)
            fragment.show(supportFragmentManager, "productDetails")
        }
        binding.rvCatalog.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            this.adapter = adapter
        }
        binding.rvCatalog.adapter?.notifyDataSetChanged()
    }

    private fun setButtons() = with(binding) {
        btnFilterCatalog.setOnClickListener {
            val fragment = FilterFragment.newInstance()
            fragment.show(supportFragmentManager, "filter")
        }
        btnSearchCatalog.setOnClickListener {
            val fragment = vm.initialProducts.value?.let { it1 -> SearchFragment.newInstance(it1) }
            fragment?.show(supportFragmentManager, "search")
        }
    }

    private fun showLoading() = with(binding) {
        pbLoadingCatalog.visibility = View.VISIBLE
        tvEmptyCatalog.visibility = View.VISIBLE
        tvEmptyCatalog.text = getString(R.string.loading)
    }

    private fun showContent() = with(binding) {
        pbLoadingCatalog.visibility = View.GONE
        tvEmptyCatalog.visibility = View.GONE
    }

    private fun showEmpty() = with(binding) {
        pbLoadingCatalog.visibility = View.GONE
        tvEmptyCatalog.visibility = View.VISIBLE
        tvEmptyCatalog.text = getString(R.string.empty_catalog)
    }

    fun filterProducts(filters: List<FilterType>) {
        vm.filterProducts(filters)
    }
    fun getFilters(): List<FilterType> {
        return vm.getFilters()
    }
}