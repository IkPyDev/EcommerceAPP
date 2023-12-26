package com.ikpydev.ecommerceapp.presention.cards

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ikpydev.ecommerceapp.databinding.AllCardsFragmentBinding
import com.ikpydev.ecommerceapp.domain.module.Card
import com.ikpydev.ecommerceapp.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllCardsFragment : BaseFragment<AllCardsFragmentBinding>(AllCardsFragmentBinding::inflate) {

    private val viewModel by viewModels<AllCardsViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData()= with(binding) {

        viewModel.loading.observe(viewLifecycleOwner){
            loading.root.isVisible = it
        }
        viewModel.cards.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                empty.isVisible = false
                cards.isVisible = true

                cards.adapter = AllCardsAdapter(it,this@AllCardsFragment::setOnclick)
            }else{
                cards.isVisible = false
                empty.isVisible = true
            }
        }
    }

    private fun initUi()= with(binding) {
        back.setOnClickListener {
            findNavController().popBackStack()
        }
        delete.setOnClickListener {
            viewModel.clearCards()
        }
        add.setOnClickListener {
            findNavController().navigate(AllCardsFragmentDirections.toAddCardFragment())
        }

    }

    fun setOnclick(card:Card){

    }

}