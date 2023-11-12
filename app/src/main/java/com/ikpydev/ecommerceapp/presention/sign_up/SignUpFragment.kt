package com.ikpydev.ecommerceapp.presention.sign_up

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
import com.ikpydev.ecommerceapp.databinding.SingUpFragmentBinding
import com.ikpydev.ecommerceapp.presention.sign_in.SignInFragmentDirections
import com.ikpydev.ecommerceapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: SingUpFragmentBinding

    private val viewModel by viewModels<SignUpViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SingUpFragmentBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        susbsirceToLiveData()
    }

    private fun initUi() = with(binding) {
        register.setOnClickListener {
            if (!username.text.isNullOrBlank() && !email.text.isNullOrBlank() && !password.text.isNullOrBlank()) {
                viewModel.signUp(
                    username.text.toString(),
                    email.text.toString(),
                    password.text.toString()
                )
            } else Toast.makeText(
                requireContext(),
                "None of the Rows should be Beginning",
                Toast.LENGTH_SHORT
            ).show()
        }
        singUp.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.toSignupFragment())
        }


    }

    private fun susbsirceToLiveData() = with(binding) {
        viewModel.loading.observe(viewLifecycleOwner) { isloading ->
            progress.isVisible = isloading
            register.text =
                if (isloading) null else root.context.getString(R.string.fragment_register)
        }
        viewModel.events.observe(viewLifecycleOwner){
            when(it){
                SignUpViewModel.Events.AlreadyRegistered -> toast(R.string.already)
                SignUpViewModel.Events.ConnectionError -> toast(R.string.connect_error)
                SignUpViewModel.Events.Error -> toast(R.string.Error)
                SignUpViewModel.Events.HomeNavigate -> toast(R.string.app_name)
            }
        }

    }
}
