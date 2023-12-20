package com.ikpydev.ecommerceapp.presention.checkout

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikpydev.ecommerceapp.domain.module.Card
import com.ikpydev.ecommerceapp.domain.module.UserInfo
import com.ikpydev.ecommerceapp.domain.repo.OrderRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val orderRepository: OrderRepository
):ViewModel(){



    val userInfo = MutableLiveData<UserInfo>()
    init {
        getUser()
    }


    private fun getUser() = viewModelScope.launch {
        userInfo.postValue( orderRepository.getUser())


        }


    fun saveUser(userInfo: UserInfo)=viewModelScope.launch{
        orderRepository.setUser(userInfo)
    }

}