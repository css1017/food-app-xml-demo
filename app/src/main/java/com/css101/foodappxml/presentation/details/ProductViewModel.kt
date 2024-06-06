package com.css101.foodappxml.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.css101.domain.models.Products

class ProductViewModel : ViewModel() {

    private var mutableProduct = MutableLiveData<Products>()
    val products: LiveData<Products> = mutableProduct

    fun saveProduct(product: Products?) {
        if (mutableProduct.value == null){
            mutableProduct.postValue(product)
        }
    }
}