package com.css101.foodappxml.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.css101.domain.models.Categories
import com.css101.domain.models.Products
import com.css101.domain.usecases.GetCategoriesUseCase
import com.css101.domain.usecases.GetProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    private var mutableProducts = MutableLiveData<List<Products>>()
    val products: LiveData<List<Products>> = mutableProducts

    lateinit var categoriesAdapter: MainCategoriesAdapter
    lateinit var categoriesLayoutManager: LinearLayoutManager
    fun getProducts() {
        if (mutableProducts.value.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val products = getProductsUseCase.execute()
                mutableProducts.postValue(products)
            }
        }
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
    val selectedCategory: LiveData<Int> = mutableSelectedCategory

    fun saveSelectedCategory(id: Int) {
        mutableSelectedCategory.postValue(id)
    }
}