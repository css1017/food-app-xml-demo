package com.css101.domain.usecases

import com.css101.domain.models.Products
import com.css101.domain.repository.ProductsRepo

class GetProductsUseCase(private val repo: ProductsRepo) {

    fun execute(): List<Products> {
        return repo.getProductList() ?: emptyList()
    }
}