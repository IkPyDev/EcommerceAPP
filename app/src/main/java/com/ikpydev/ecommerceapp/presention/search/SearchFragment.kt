package com.ikpydev.ecommerceapp.presention.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.databinding.SearchFragmentBinding
import com.ikpydev.ecommerceapp.presention.search.adapter.RecentsAdapter
import com.ikpydev.ecommerceapp.presention.search.adapter.SearchProductAdapter
import com.ikpydev.ecommerceapp.utils.hideKeyboard
import com.ikpydev.ecommerceapp.utils.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: SearchFragmentBinding
    private val viewModel by viewModels<SearchViewModel>()
    private val args by navArgs<SearchFragmentArgs>()
    private val adapter by lazy { SearchProductAdapter(this::onProductClick, this::like) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args.category?.let { viewModel.setCategory(it) }
        adapter.addLoadStateListener {
            viewModel.setLoadStates(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragmentBinding.inflate(inflater)
        return binding.root
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
            isRecentsVisible(search.search.hasFocus())
        }

    }

    private fun initUi() = with(binding) {
        search.search.requestFocus()
        showKeyboard()
        close.setOnClickListener {
            findNavController().popBackStack()
        }

        product.adapter = adapter

        search.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.setSearch(search.search.text.toString())
                viewLifecycleOwner.lifecycleScope.launch {
                    adapter.submitData(PagingData.empty())
                }

                hideKeyboard()
                search.search.clearFocus()
                return@setOnEditorActionListener true
            }
            true
        }

        search.search.setOnFocusChangeListener { view, focused ->
            isRecentsVisible(focused)

        }

        clear.setOnClickListener {
            viewModel.clearRecent()
        }


    }

    private fun SearchFragmentBinding.isRecentsVisible(focused: Boolean) {
        listOf(resent, resentTitle, clear).forEach {
            it.isVisible = viewModel.recents.value.isNullOrEmpty() && focused
        }
    }

    private fun onProductClick(product: Product) {

    }

    private fun like(product: Product) {

    }

    private fun onRecentClick(resent: String) {

    }

}