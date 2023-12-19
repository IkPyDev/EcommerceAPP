package com.ikpydev.ecommerceapp.presention.card

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.databinding.CardFragmentBinding
import com.ikpydev.ecommerceapp.domain.module.Cart
import com.ikpydev.ecommerceapp.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardFragment : BaseFragment<CardFragmentBinding>(CardFragmentBinding::inflate) {

    private val viewModel by viewModels<CardViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() = with(binding) {
        viewModel.carts.observe(viewLifecycleOwner) {
            product.adapter = CartAdapter(it, this@CardFragment::onClick, this@CardFragment::set)
            empty.isVisible = it.isEmpty()
            content.isVisible = it.isNotEmpty()
        }
        viewModel.evets.observe(viewLifecycleOwner) {
            val message = when (it) {
                CardViewModel.Event.BillingError -> R.string.card_billing_error

                CardViewModel.Event.OrderError -> R.string.card_order_error
            }
            Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.billing_loading.observe(viewLifecycleOwner) {
            progressApply.isVisible = it
            apply.text = if (it) null else getString(R.string.card_apply)
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            progressCheckout.isVisible = it
            checkout.text = if (it) null else getString(R.string.card_checkout)
        }
        viewModel.billing.observe(viewLifecycleOwner) { billing ->
            itemTotal.text = getString(R.string.card_price, billing.items)
            deliverFree.text = getString(R.string.card_price_plus, billing.delivery)
            tax.text = getString(R.string.card_price_plus, billing.tex)
            discount.text = getString(R.string.card_price_minus, billing.discount ?: 0.0)
            listOf(discount, discountTitle).forEach {
                it.isVisible = billing.discount != null
            }
            order.text = getString(R.string.card_price, billing.total)
        }
    }

    private fun initUI() = with(binding) {
        back.setOnClickListener {
            findNavController().popBackStack()
        }
        delete.setOnClickListener {
            viewModel.clear()
        }
        apply.setOnClickListener {
            viewModel.getBilling(promo.text.toString())
        }
        checkout.setOnClickListener {
            findNavController().navigate(CardFragmentDirections.toCheckoutFragment(promo.text.toString()))
        }
    }

    private fun onClick(cart: Cart) {
        findNavController().navigate(CardFragmentDirections.toDetailFragment(cart.id))
    }

    private fun set(cart: Cart) {
        viewModel.set(cart)
    }


}