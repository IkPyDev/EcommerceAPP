package com.ikpydev.ecommerceapp.presention.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.databinding.SearchFragmentBinding
import com.ikpydev.ecommerceapp.domain.module.ProductQuery
import com.ikpydev.ecommerceapp.presention.filter.FilterFragment
import com.ikpydev.ecommerceapp.presention.search.SearchFragmentDirections.toFilterFragment
import com.ikpydev.ecommerceapp.presention.search.adapter.RecentsAdapter
import com.ikpydev.ecommerceapp.presention.search.adapter.SearchProductAdapter
import com.ikpydev.ecommerceapp.utils.BaseFragment
import com.ikpydev.ecommerceapp.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchFragmentBinding>(SearchFragmentBinding::inflate) {

    private val viewModel by viewModels<SearchViewModel>()
    private val args by navArgs<SearchFragmentArgs>()
    private val adapter by lazy { SearchProductAdapter(this::onProductClick, this::like) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setInitials(args.category, args.wishlist)
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

        viewModel.product.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(it)
            }
        }

        viewModel.recents.observe(viewLifecycleOwner) {
            resent.adapter = RecentsAdapter(it, this@SearchFragment::onRecentClick)
            isRecentsVisible(searchContainer.search.hasFocus())
        }

    }

    private fun initUi() = with(binding) {
        searchContainer.search.requestFocus()

        close.setOnClickListener {
            findNavController().popBackStack()
        }

        product.adapter = adapter

        searchContainer.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.setSearch(searchContainer.search.text.toString())
                viewLifecycleOwner.lifecycleScope.launch {
                    adapter.submitData(PagingData.empty())
                }

                hideKeyboard()
                searchContainer.search.clearFocus()
                return@setOnEditorActionListener true
            }
            false
        }

        searchContainer.search.setOnFocusChangeListener { _, focused ->
            isRecentsVisible(focused)

        }

        clear.setOnClickListener {
            viewModel.clearRecent()
        }


        searchContainer.filter.setOnClickListener {
            val query = viewModel.query.value ?: ProductQuery()
            findNavController().navigate(toFilterFragment(query))
        }

        setFragmentResultListener(FilterFragment.REQUEST_KEY){_,result ->
            val query = result.getParcelable<ProductQuery>(FilterFragment.RESULT_KEY)
            viewModel.setQuery(query ?: return@setFragmentResultListener)
            searchContainer.search.clearFocus()
            hideKeyboard()
            isRecentsVisible(false)
        }


    }

    private fun SearchFragmentBinding.isRecentsVisible(focused: Boolean) {
        listOf(resent, resentTitle, clear).forEach {
            it.isVisible = viewModel.recents.value.isNullOrEmpty().not() && focused
        }
    }

    private fun onProductClick(product: Product) {
        findNavController().navigate(SearchFragmentDirections.toDetailFragment(product.id))
    }

    private fun like(product: Product) {

    }

    private fun onRecentClick(resent: String) {

    }

}