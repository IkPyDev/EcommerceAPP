package com.ikpydev.ecommerceapp.presention.sign_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.databinding.SingInFragmentBinding
import com.ikpydev.ecommerceapp.presention.sign_up.SignUpFragment
import com.ikpydev.ecommerceapp.presention.sign_up.SignUpFragmentDirections
import com.ikpydev.ecommerceapp.utils.BaseFragment
import com.ikpydev.ecommerceapp.utils.clearLightStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<SingInFragmentBinding>(SingInFragmentBinding::inflate) {

    private val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        susbsirceToLiveData()
    }

    private fun susbsirceToLiveData() = with(binding) {
        viewModel.loading.observe(viewLifecycleOwner) {isloading ->
            progress.isVisible = isloading
            singIn.text = if (isloading) null else root.context.getString(R.string.fragment_sing_in)
        }
        viewModel.event.observe(viewLifecycleOwner){
            when(it){
                SignInViewModel.Event.ConnectionError -> toast(R.string.connect_error)
                SignInViewModel.Event.Error -> toast(R.string.Error)
                SignInViewModel.Event.InvalidCredentails -> toast(R.string.InvalidEror)
                SignInViewModel.Event.Seccefull -> toast(R.string.app_name)
            }
        }
    }

    private fun initUi() = with(binding) {
        clearLightStatusBar()
        singIn.setOnClickListener {
            viewModel.sinIn(username.text.toString(), password.text.toString())

        }
        singUp.setOnClickListener{
            findNavController().navigate(SignUpFragmentDirections.toSignupFragment())

        }
    }

    private fun toast(message: Int){
        Toast.makeText(requireContext(), getString(message), Toast.LENGTH_SHORT).show()
    }

}