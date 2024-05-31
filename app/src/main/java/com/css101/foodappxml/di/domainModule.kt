package com.css101.foodappxml.di

import com.css101.domain.usecases.GetCategoriesUseCase
import com.css101.domain.usecases.GetProductsUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetProductsUseCase(get())
    }
    factory {
        GetCategoriesUseCase(get())
    }
}