package com.css101.foodappxml.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.css101.domain.models.Categories
import com.css101.domain.models.Products
import com.css101.domain.usecases.GetCategoriesUseCase
import com.css101.domain.usecases.GetProductsUseCase
import com.css101.foodappxml.presentation.filter.FilterType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    private var mutableInitialProducts = MutableLiveData<List<Products>>()
    val initialProducts: LiveData<List<Products>> = mutableInitialProducts
    private var mutableFilteredProducts = MutableLiveData<List<Products>>()
    val products: LiveData<List<Products>> = mutableFilteredProducts

    private var selectedFilters = mutableListOf<FilterType>()
    private var selectedCategory: Int? = null

    lateinit var categoriesAdapter: MainCategoriesAdapter
    lateinit var categoriesLayoutManager: LinearLayoutManager
    fun getProducts() {
        if (mutableInitialProducts.value.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val products = getProductsUseCase.execute()
                mutableInitialProducts.postValue(products)
                mutableFilteredProducts.postValue(products)
            }
        }
    }

    fun filterProducts(filters: List<FilterType>) {
        selectedFilters.clear()
        selectedFilters.addAll(filters)
        applyFiltersAndCategory()
    }

    fun filterByCategory(category: Int) {
        selectedCategory = category
        applyFiltersAndCategory()
    }
    private fun applyFiltersAndCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val filteredProducts = mutableInitialProducts.value?.filter { product ->
                val matchesCategory = selectedCategory?.let { product.categoryId == it } ?: true
                val matchesFilters = selectedFilters.all { filter -> product.tagIds.contains(filter.id) }
                matchesCategory && matchesFilters
            }
            mutableFilteredProducts.postValue(filteredProducts)
        }
    }

    fun getFilters(): List<FilterType>{
        return selectedFilters
    }

    private var mutableCategories = MutableLiveData<List<Categories>>()
    val categories: LiveData<List<Categories>> = mutableCategories

    fun getCategories() {
        if (mutableCategories.value.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val categories = getCategoriesUseCase.execute()
                mutableCategories.postValue(categories)
            }
        }
    }

    private var mutableSelectedCategory = MutableLiveData<Int>()
    val selectedCategoryLive: LiveData<Int> = mutableSelectedCategory

    fun saveSelectedCategory(id: Int) {
        mutableSelectedCategory.postValue(id)
    }
}