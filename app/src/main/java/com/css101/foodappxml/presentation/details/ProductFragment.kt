package com.css101.foodappxml.presentation.details

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.css101.domain.models.Products
import com.css101.foodappxml.R
import com.css101.foodappxml.databinding.FragmentProductDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class ProductFragment : BottomSheetDialogFragment() {

    private val vm by viewModel<ProductViewModel>()
    private lateinit var binding: FragmentProductDetailsBinding
    private var product: Products? = null

    companion object {
        fun newInstance(products: Products): ProductFragment {
            return ProductFragment().apply {
                product = products
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
        vm.saveProduct(product)
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        vm.products.observe(this@ProductFragment) {
            tvDescProductDetails.text = it?.description
            tvNameProductDetails.text = it?.name
            val price = (it?.priceCurrent?.div(100))?.toInt()

            btnAddToCartDetails.text = getString(R.string.to_cart_for_rub, price)
            btnBackProductDetails.setOnClickListener { dialog?.dismiss() }
            inclParameters.tvCaloriesValueDetails.text =
                getString(R.string._kkal, formatDouble(it?.energyPer100Grams))
            inclParameters.tvCarbsValueDetails.text =
                getString(R.string._gram, formatDouble(it?.carbohydratesPer100Grams))
            inclParameters.tvFatsValueDetails.text =
                getString(R.string._gram, formatDouble(it?.fatsPer100Grams))
            inclParameters.tvWeightValueDetails.text =
                getString(R.string.product_weight, it?.measure?.toInt(), it?.measureUnit)
            inclParameters.tvProteinValueDetails.text =
                getString(R.string._gram, formatDouble(it?.proteinsPer100Grams))
        }
    }

    private fun formatDouble(doubleValue: Double?): String {
        val decimalFormat = DecimalFormat("#,##0,0")
        return decimalFormat.format(doubleValue)
    }
}