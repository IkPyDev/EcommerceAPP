package com.ikpydev.ecommerceapp.presention.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ikpydev.ecommerceapp.databinding.ProfileFragmentBinding
import com.ikpydev.ecommerceapp.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding>(ProfileFragmentBinding::inflate) {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() = with(binding) {

        viewModel.loading.observe(viewLifecycleOwner){
            loading.root.isVisible = it
        }

        viewModel.user.observe(viewLifecycleOwner) {
            Glide.with(root.context).load(it.avatar).into(avatar)
            fullName.text = "${it.firstName} ${it.lastName}"
            email.text = it.email
        }
    }

    private fun initUi() = with(binding) {

        logoutLayout.setOnClickListener {
            viewModel.logOut()
            findNavController().navigate(ProfileFragmentDirections.toSingInFragment())
        }

        cardsLayout.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.allCardsFragment())
        }


    }


}

