package com.ikpydev.ecommerceapp.presention.checkout_pay

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.ikpydev.ecommerceapp.databinding.CheckoutPayFragmentBinding
import com.ikpydev.ecommerceapp.utils.BaseFragment

class CheckoutPayFragment:BaseFragment<CheckoutPayFragmentBinding>(CheckoutPayFragmentBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi()= with(binding) {
        addCard.setOnClickListener {
            findNavController().navigate(CheckoutPayFragmentDirections.toAddCardFragment())
        }

    }
}