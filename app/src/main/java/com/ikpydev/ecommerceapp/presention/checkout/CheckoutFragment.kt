package com.ikpydev.ecommerceapp.presention.checkout

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.databinding.CheckoutFragmentBinding
import com.ikpydev.ecommerceapp.domain.module.UserInfo
import com.ikpydev.ecommerceapp.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : BaseFragment<CheckoutFragmentBinding>(CheckoutFragmentBinding::inflate) {
    private val items = arrayOf(
        "Toshkent", "Andijon", "Namangan", "Farg'ona", "Sirdaryo",
        "Jizzax", "Samarqand", "Buxoro", "Navoiy", "Qashqadaryo",
        "Surxondaryo", "Xorazm", "Karakalpakistan"
    )

    private lateinit var infoUser: UserInfo
    private val viewModel by viewModels<CheckoutViewModel>()
    private val args by navArgs<CheckoutFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() = with(binding) {
        viewModel.userInfo.observe(viewLifecycleOwner) { user: UserInfo ->
            infoUser = user
            fullName.text = Editable.Factory.getInstance().newEditable(user.fullName)
            email.text = Editable.Factory.getInstance().newEditable(user.emailAddress)
            phone.text = Editable.Factory.getInstance().newEditable(user.phoneNumber)
            addAddress.text = Editable.Factory.getInstance().newEditable(user.address)
            zip.text = Editable.Factory.getInstance().newEditable(user.code)
            city.text = Editable.Factory.getInstance().newEditable(user.city)

            val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)



            count.setAdapter(adapter)
            count.setText(user.country, false)


        }
    }

    private fun initUi() = with(binding) {
        back.setOnClickListener {
            findNavController().popBackStack()
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)

        count.setAdapter(adapter)

        next.setOnClickListener {
            if (save.isChecked) {

                val info = UserInfo(
                    fullName = fullName.text.toString(),
                    emailAddress = email.text.toString(),
                    phoneNumber = phone.text.toString(),
                    address = addAddress.text.toString(),
                    code = zip.text.toString(),
                    city = city.text.toString(),
                    country = count.text.toString(),
                    promo = args.promo


                )
                infoUser = info
                viewModel.saveUser(info)
            }

            findNavController().navigate(CheckoutFragmentDirections.checkoutPayFragment(infoUser))
        }

    }


}