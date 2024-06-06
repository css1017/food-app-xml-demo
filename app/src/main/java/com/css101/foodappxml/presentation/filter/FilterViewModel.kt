package com.css101.foodappxml.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class FilterViewModel: ViewModel() {


    private val _filters = MutableLiveData<List<FilterType>>()
    val filters: LiveData<List<FilterType>> = _filters

    fun addFilter(type: FilterType) {
        val currentFilters = _filters.value ?: emptyList()
        if (!currentFilters.contains(type)) {
            _filters.value = (currentFilters + type)
        }
    }

    fun removeFilter(type: FilterType) {
        val currentFilters = _filters.value ?: emptyList()
        if (currentFilters.contains(type)) {
            _filters.value = (currentFilters - type)
        }
    }
}