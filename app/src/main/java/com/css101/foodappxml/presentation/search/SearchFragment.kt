package com.css101.foodappxml.presentation.search

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.css101.domain.models.Products
import com.css101.foodappxml.R
import com.css101.foodappxml.databinding.FragmentSearchBinding
import com.css101.foodappxml.presentation.details.ProductFragment
import com.css101.foodappxml.presentation.utils.showKeyboard
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BottomSheetDialogFragment() {

    private val vm by viewModel<SearchViewModel>()
    private lateinit var binding: FragmentSearchBinding
    private var _products: List<Products>? = null

    companion object {
        fun newInstance(products: List<Products>): SearchFragment {
            return SearchFragment().apply {
                _products = products
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        dialog?.window?.setDimAmount(0.0F)
        sheetContainer.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog
            ?.window
            ?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm.saveProduct(_products)
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        setupContent()
        showStart()
        vm.filteredProducts.observe(this@SearchFragment) {
            setAdapter(it)
        }
    }

    private fun setupContent() = with(binding) {
        btnBackSearch.setOnClickListener { dialog?.dismiss() }
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                vm.search(s)
            }
        })
        etSearch.requestFocus()
            etSearch.showKeyboard()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAdapter(products: List<Products>) {
        if (products.isEmpty()) {
            showEmpty()
        } else showContent()
        val adapter = SearchAdapter(products) {
            val fragment = ProductFragment.newInstance(it)
            fragment.show(parentFragmentManager, "productDetails")
        }
        binding.rvSearch.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            this.adapter = adapter
        }
        binding.rvSearch.adapter?.notifyDataSetChanged()
    }

    private fun showContent() = with(binding) {
        tvEmptySearch.visibility = View.GONE
    }

    private fun showEmpty() = with(binding) {
        tvEmptySearch.visibility = View.VISIBLE
        tvEmptySearch.text = getString(R.string.nothing_was_found)
    }
    private fun showStart() = with(binding) {
        tvEmptySearch.visibility = View.VISIBLE
    }
}