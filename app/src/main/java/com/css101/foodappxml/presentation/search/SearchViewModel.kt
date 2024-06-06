package com.css101.foodappxml.presentation.search

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.css101.domain.models.Products

class SearchViewModel : ViewModel() {

    private var mutableInitialProducts = MutableLiveData<List<Products>>()
    private var filteredMutableProducts = MutableLiveData<List<Products>>()
    val filteredProducts: LiveData<List<Products>> = filteredMutableProducts

    fun saveProduct(products: List<Products>?) {
        if (mutableInitialProducts.value == null){
            mutableInitialProducts.postValue(products)
        }
    }
    fun search(query: Editable?){
        val string = query.toString()
        if (query != null && string != ""){
            val filteredProducts = mutableInitialProducts.value?.filter { product ->
                product.description.contains(string) || product.name.contains(string)
            }
            filteredMutableProducts.postValue(filteredProducts)
        } else {
            filteredMutableProducts.postValue(emptyList())

        }
    }
}