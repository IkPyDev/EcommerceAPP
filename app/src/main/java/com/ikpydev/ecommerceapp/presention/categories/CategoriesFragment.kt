package com.ikpydev.ecommerceapp.presention.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ikpydev.ecommerceapp.data.api.product.dto.Category
import com.ikpydev.ecommerceapp.databinding.CategoriesFragmentBinding
import com.ikpydev.ecommerceapp.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : BaseFragment<CategoriesFragmentBinding>(CategoriesFragmentBinding::inflate) {



    private val viewModel by viewModels<CategoriesViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        subscribeToLiveData()
    }

    private fun initUi() = with(binding) {

        back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun subscribeToLiveData() = with(binding) {
        viewModel.loading.observe(viewLifecycleOwner) {
            loading.root.isVisible = it
        }
        viewModel.error.observe(viewLifecycleOwner) {
            error.root.isVisible = it
        }
        viewModel.categories.observe(viewLifecycleOwner) {
            categories.adapter =CategoriesAdapter(it,this@CategoriesFragment::onCategoryClick)


        }


    }
    private fun onCategoryClick(category: Category){
        findNavController().navigate(CategoriesFragmentDirections.toProductFragment(category))

    }
}

