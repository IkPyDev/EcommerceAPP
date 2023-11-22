package com.ikpydev.ecommerceapp.presention.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ikpydev.ecommerceapp.data.api.product.dto.Product
import com.ikpydev.ecommerceapp.databinding.ItemProductBinding
import com.ikpydev.ecommerceapp.databinding.ProductFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private lateinit var binding: ProductFragmentBinding
    private val viewModel by viewModels<ProductViewModel>()
    private val adapter by lazy { ProductAdapter(this::onClick,this::liked) }
    private val args by navArgs<ProductFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setCategory(args.category)
        adapter.addLoadStateListener {
            viewModel.setLoadStates(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductFragmentBinding.inflate(inflater)
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
        viewModel.error.observe(viewLifecycleOwner) {
            error.root.isVisible = it

        }
        viewModel.product.observe(viewLifecycleOwner){
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(it)
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

    }

    private fun liked(product: Product) {

    }
}