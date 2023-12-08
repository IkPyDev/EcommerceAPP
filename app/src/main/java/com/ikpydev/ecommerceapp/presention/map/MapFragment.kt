package com.ikpydev.ecommerceapp.presention.map

import android.os.Bundle
import android.view.View
import com.ikpydev.ecommerceapp.databinding.MapFragmentBinding
import com.ikpydev.ecommerceapp.utils.BaseFragment
import java.lang.Exception

class MapFragment: BaseFragment<MapFragmentBinding>(MapFragmentBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        throw Exception("test exception")
    }
}