package com.css101.foodappxml.di

import com.css101.foodappxml.presentation.main.MainViewModel
import com.css101.foodappxml.presentation.details.ProductViewModel
import com.css101.foodappxml.presentation.filter.FilterViewModel
import com.css101.foodappxml.presentation.search.SearchViewModel
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
    viewModel {
        SearchViewModel()
    }
    viewModel {
        FilterViewModel()
    }
}
