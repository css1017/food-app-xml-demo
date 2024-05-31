package com.css101.domain.usecases

import com.css101.domain.models.Categories
import com.css101.domain.repository.ProductsRepo

class GetCategoriesUseCase(private val repo: ProductsRepo) {

    fun execute(): List<Categories> {
        return repo.getCategoriesList() ?: emptyList()
    }
}