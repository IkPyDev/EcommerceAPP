package com.ikpydev.ecommerceapp.presention.sing_in

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ikpydev.ecommerceapp.R
import com.ikpydev.ecommerceapp.databinding.SingInFragmentBinding

class SingInFragment : Fragment() {

    private lateinit var binding: SingInFragmentBinding
    private val viewModel by viewModels<SingInViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SingInFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        susbsirceToLiveData()
    }

    private fun susbsirceToLiveData() = with(binding) {
        viewModel.loading.observe(viewLifecycleOwner) {isloading ->
            progressBtn.isVisible = isloading
        }
        viewModel.event.observe(viewLifecycleOwner){
            when(it){
                SingInViewModel.Event.ConnectionError -> toast(R.string.connect_error)
                SingInViewModel.Event.Error -> toast(R.string.Error)
                SingInViewModel.Event.InvalidCredentails -> toast(R.string.InvalidEror)
            }
        }
    }

    private fun initUi() = with(binding) {
        sinInBtn.setOnClickListener {
            viewModel.sinIn(usernameTv.text.toString(), passwordTv.text.toString())
        }
    }
    private fun toast(message: Int){
        Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
    }

}