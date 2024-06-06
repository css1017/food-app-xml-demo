package com.css101.foodappxml.presentation.filter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.css101.foodappxml.databinding.DialogFiltersBinding
import com.css101.foodappxml.presentation.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterFragment : BottomSheetDialogFragment() {

    private val vm by viewModel<FilterViewModel>()

    private lateinit var binding: DialogFiltersBinding


    companion object {
        fun newInstance(): FilterFragment {
            return FilterFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setDimAmount(0.0F)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        setButtons()
        val filters = (activity as MainActivity).getFilters()
        filters.forEach {
            when (it) {
                FilterType.Vegan -> {
                    checkboxVeganFilters.isChecked = true
                    vm.addFilter(FilterType.Vegan)
                }

                FilterType.Sale -> {
                    checkboxSaleFilters.isChecked = true
                    vm.addFilter(FilterType.Sale)
                }

                FilterType.Spicy -> {
                    checkboxSpicyFilters.isChecked = true
                    vm.addFilter(FilterType.Spicy)
                }

                else -> {}
            }
        }
        vm.filters.observe(this@FilterFragment) {
            (activity as MainActivity).filterProducts(it)
        }

    }

    private fun setButtons() = with(binding) {
        btnSaveFilters.setOnClickListener {
            dialog?.dismiss()
        }
        checkboxSaleFilters.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vm.addFilter(FilterType.Sale)
            } else {
                vm.removeFilter(FilterType.Sale)
            }
        }
        checkboxSpicyFilters.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vm.addFilter(FilterType.Spicy)
            } else {
                vm.removeFilter(FilterType.Spicy)
            }
        }
        checkboxVeganFilters.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vm.addFilter(FilterType.Vegan)
            } else {
                vm.removeFilter(FilterType.Vegan)
            }
        }
    }


}