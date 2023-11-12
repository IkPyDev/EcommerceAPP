package com.ikpydev.ecommerceapp.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: Int){
    Toast.makeText(requireContext(), getString(message), Toast.LENGTH_SHORT).show()
}