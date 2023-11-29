package com.ikpydev.ecommerceapp.presention.filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.data.api.product.dto.Category
import com.ikpydev.ecommerceapp.databinding.FilterFragmentBinding
import com.ikpydev.ecommerceapp.databinding.ItemRadioButtonBinding
import com.ikpydev.ecommerceapp.domain.module.ProductQuery
import com.ikpydev.ecommerceapp.domain.module.Sort
import com.ikpydev.ecommerceapp.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : BaseFragment<FilterFragmentBinding>(FilterFragmentBinding::inflate) {

    private val viewModel by viewModels<FilterViewModel>()
    private val args by navArgs<FilterFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() = with(binding) {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            listOf(categoryGroup, categoryTitle, othersDivider).forEach { it.isVisible = true }

            categories.forEach {
                val radioBinding = ItemRadioButtonBinding.inflate(layoutInflater)
                radioBinding.root.text = it.title
                radioBinding.root.tag = it
                radioBinding.root.isChecked = args.filter.category?.id == it.id
                categoryGroup.addView(radioBinding.root)}

        }
        viewModel.events.observe(viewLifecycleOwner) {
            when (it) {
                Event.CategoryError -> {
                    val snackBar = Snackbar.make(
                        binding.root,
                        R.string.fragment_filter_category_error,
                        Snackbar.LENGTH_INDEFINITE
                    )
                    snackBar.setAction(R.string.retry) {
                        snackBar.dismiss()
                        viewModel.getCategories()
                    }
                    snackBar.show()
                }
            }
        }
    }

    private fun initUi() = with(binding) {
        close.setOnClickListener {
            findNavController().popBackStack()
        }

        val filter = args.filter
        rangeSlinder.values = filter.range.toList()
        filter.rating?.let {
            (ratingGroup.getChildAt(it) as RadioButton).isChecked = true
        }
        filter.discount?.let {id ->
            (discountGroup.getChildAt(id) as RadioButton).isChecked = true
        }
        discountSort.isChecked = filter.sort?.contains(Sort.discount) ?: false
        shippingSort.isChecked = filter.sort?.contains(Sort.shipping) ?: false
        deliverySort.isChecked = filter.sort?.contains(Sort.delivery) ?: false
        voucherSort.isChecked = filter.sort?.contains(Sort.voucher) ?: false

        apply.setOnClickListener {
            val sort = mutableListOf<Sort>()
            if (shippingSort.isChecked) sort.add(Sort.shipping)
            if (discountSort.isChecked) sort.add(Sort.discount)
            if (deliverySort.isChecked) sort.add(Sort.delivery)
            if (voucherSort.isChecked) sort.add(Sort.voucher)
            val query = ProductQuery(
                category = categoryGroup.children.map { it as RadioButton }
                    .firstOrNull() { it.isChecked }?.tag as? Category,
                search = args.filter.search,
                range = rangeSlinder.values[0] to rangeSlinder.values[1],
                rating = ratingGroup.children
                    .mapIndexed { index, view -> index to (view as RadioButton).isChecked }
                    .firstOrNull() { it.second }?.first,
                discount = discountGroup.children
                    .mapIndexed { index, view -> index to (view as RadioButton).isChecked }
                    .firstOrNull { it.second }?.first,
                sort = sort


            )
            val result = bundleOf(RESULT_KEY to query)
            setFragmentResult(REQUEST_KEY, result)
            findNavController().popBackStack()
        }
    }

    companion object {
        const val REQUEST_KEY = "filter_request_key"
        const val RESULT_KEY = "filter_result_key"
    }

}