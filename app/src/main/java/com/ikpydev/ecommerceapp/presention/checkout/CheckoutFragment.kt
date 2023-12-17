package com.ikpydev.ecommerceapp.presention.checkout

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.ikpydev.ecommerceapp.data.store.BaseStore
import com.ikpydev.ecommerceapp.databinding.CheckoutFragmentBinding
import com.ikpydev.ecommerceapp.presention.card.CardFragmentDirections
import com.ikpydev.ecommerceapp.utils.BaseFragment

class CheckoutFragment: BaseFragment<CheckoutFragmentBinding>(CheckoutFragmentBinding::inflate)  {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi()= with(binding) {
        next.setOnClickListener {
            findNavController().navigate(CheckoutFragmentDirections.checkoutPayFragment())
        }

    }
}