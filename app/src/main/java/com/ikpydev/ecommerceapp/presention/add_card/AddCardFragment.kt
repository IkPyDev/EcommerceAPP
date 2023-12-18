package com.ikpydev.ecommerceapp.presention.add_card

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ikpydev.ecommerceapp.common.Constats.CardData
import com.ikpydev.ecommerceapp.common.Constats.CardNumber
import com.ikpydev.ecommerceapp.databinding.AddCardFragmentBinding
import com.ikpydev.ecommerceapp.domain.module.Card
import com.ikpydev.ecommerceapp.utils.BaseFragment
import com.ikpydev.ecommerceapp.utils.hideActionBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCardFragment:BaseFragment<AddCardFragmentBinding>(AddCardFragmentBinding::inflate) {
    private val viewModel by viewModels<AddCardViewModel>()

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() = with(binding) {

        back.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.editTextControl(addNumber, cardNumber, CardNumber)

        viewModel.editTextControl(addCard, name, CardNumber)

        viewModel.editTextControl(year, data, CardData)

        add.setOnClickListener {

            if (name.text.isNullOrBlank().not() && cardNumber.text.isNullOrBlank()
                    .not() && data.text.isNullOrBlank().not() && cvv.text.isNullOrBlank().not()
            ) {
                val card = Card(
                    cardName = name.text.toString(),
                    cardNumber = cardNumber.text.toString(),
                    data = data.text.toString(),
                    svv = cvv.text.toString().toInt()
                )


                    viewModel.setCard(card)

                    findNavController().popBackStack()

            } else {
                Toast.makeText(requireContext(), "One left without filling", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
}