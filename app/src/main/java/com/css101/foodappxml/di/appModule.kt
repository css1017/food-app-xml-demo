package com.css101.foodappxml.di

import com.css101.foodappxml.presentation.MainViewModel
import com.css101.foodappxml.presentation.ProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        MainViewModel(
            getProductsUseCase = get(),
            getCategoriesUseCase = get()
        )
    }
    viewModel {
        ProductViewModel()
    }
}
