package com.ikpydev.ecommerceapp.presention.product

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.databinding.ProductFragmentBinding
import com.ikpydev.ecommerceapp.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : BaseFragment<ProductFragmentBinding>(ProductFragmentBinding::inflate) {

    private val viewModel by viewModels<ProductViewModel>()
    private val adapter by lazy { ProductAdapter(this::onClick,this::wishlist) }
    private val args by navArgs<ProductFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setCategory(args.category)
        adapter.addLoadStateListener {
            viewModel.setLoadStates(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() = with(binding) {
        viewModel.loading.observe(viewLifecycleOwner) {
            loading.root.isVisible = it
        }
        viewModel.error.observe(viewLifecycleOwner) {
            error.root.isVisible = it

        }
        viewModel.product.observe(viewLifecycleOwner) { pagingData ->
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(pagingData)
            }
        }

        viewModel.category.observe(viewLifecycleOwner){
            title.text = it.title
        }

    }

    private fun initUi() = with(binding) {
        root.setOnClickListener {
            findNavController().popBackStack()
        }
        error.retry.setOnClickListener {
            viewModel.getProduct()
        }
        product.adapter = adapter

    }

    private fun onClick(product: Product) {
        findNavController().navigate(ProductFragmentDirections.toDetailFragment(product.id))
    }

    private fun wishlist(product: Product) {
        viewModel.toggleWishlist(product)
    }
}