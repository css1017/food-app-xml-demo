package com.css101.domain.repository

import com.css101.domain.models.Categories
import com.css101.domain.models.Products


interface ProductsRepo {
    fun getProductList(): List<Products>?
    fun getCategoriesList(): List<Categories>?

}