package com.css101.foodappxml.di

import com.css101.data.repository.ProductsRepoImpl
import com.css101.domain.repository.ProductsRepo
import org.koin.dsl.module

val dataModule = module {
    single<ProductsRepo> {
        ProductsRepoImpl()
    }
}