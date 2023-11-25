package com.ikpydev.ecommerceapp.presention.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ikpydev.ecommerceapp.databinding.FilterFragmentBinding
import com.ikpydev.ecommerceapp.databinding.ItemRadioGroupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment:Fragment() {

    private lateinit var binding : FilterFragmentBinding
    private val viewModel by viewModels<FilterViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FilterFragmentBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() = with(binding) {
        viewModel.categories.observe(viewLifecycleOwner){category ->
            listOf(categoryGroup,categoryTitle,othersDivider).forEach{it.isVisible = true}

            category.forEach{
                val radioBinding = ItemRadioGroupBinding.inflate(layoutInflater)
                radioBinding.root.text = it.title
                radioBinding.root.tag  = it    
            }

        }
    }

    private fun initUi() = with(binding) {
        close.setOnClickListener {
            findNavController().popBackStack()
        }

    }

}