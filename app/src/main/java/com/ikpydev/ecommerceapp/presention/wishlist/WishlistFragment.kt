package com.ikpydev.ecommerceapp.presention.wishlist

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.databinding.WishlistFragmentBinding
import com.ikpydev.ecommerceapp.presention.product.ProductAdapter
import com.ikpydev.ecommerceapp.presention.product.ProductFragmentDirections
import com.ikpydev.ecommerceapp.presention.product.ProductViewModel
import com.ikpydev.ecommerceapp.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WishlistFragment: BaseFragment<WishlistFragmentBinding>(WishlistFragmentBinding::inflate) {

    private val viewModel by viewModels<WishlistViewModel>()
    private val adapter by lazy { ProductAdapter(this::onClick,this::liked) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter.addLoadStateListener {
            viewModel.setLoadStates(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        subscribeToLiveData()
    }

    private fun initUi() = with(binding) {
        error.retry.setOnClickListener {
            viewModel.getProduct()
        }
        product.adapter = adapter

        search.setOnClickListener {
            findNavController().navigate(WishlistFragmentDirections.toSearchFragment())
        }
        swipeRefresh.setOnRefreshListener {
            viewModel.getProduct()
        }
    }

    private fun subscribeToLiveData() = with(binding) {
        viewModel.loading.observe(viewLifecycleOwner) {
            loading.root.isVisible = it && swipeRefresh.isRefreshing.not()
            if (it.not()){
                swipeRefresh.isRefreshing = false
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            error.root.isVisible = it

        }
        viewModel.product.observe(viewLifecycleOwner) { pagingData ->
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(pagingData)
            }
        }

    }









    private fun onClick(product: Product) {
        findNavController().navigate(WishlistFragmentDirections.toDetailFragment(product.id))
    }

    private fun liked(product: Product) {

    }

}