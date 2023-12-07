package com.ikpydev.ecommerceapp.presention.orders

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.ikpydev.ecommerceapp.databinding.OrdersFragmentBinding
import com.ikpydev.ecommerceapp.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrdersFragment: BaseFragment<OrdersFragmentBinding>(OrdersFragmentBinding::inflate) {

    private val viewModel by viewModels<OrdersViewModel>()
    private val adapter by lazy { OrderAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.addLoadStateListener {
            viewModel.setLoadStates(it, adapter.itemCount)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToLiveData()
        initUi()
    }
 val i = 2
    private fun subscribeToLiveData() = with(binding) {

        viewModel.loading.observe(viewLifecycleOwner) {
            loading.progress.isVisible = it
            loading.root.isVisible = it
        }
        viewModel.error.observe(viewLifecycleOwner) {
            error.root.isVisible = it
        }
        viewModel.empty.observe(viewLifecycleOwner) {
            empty.isVisible = it
        }
        viewModel.orders.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                if (it == null) {
                    adapter.submitData(PagingData.empty())
                    return@launch
                }
                adapter.submitData(it)
            }
        }


    }

    private fun initUi() = with(binding) {
        statuses.adapter = StatusAdapter(viewModel.mStatus, viewModel::setStatus)
        error.retry.setOnClickListener {
            viewModel.getOrders()
        }
        orders.adapter = adapter
    }

}