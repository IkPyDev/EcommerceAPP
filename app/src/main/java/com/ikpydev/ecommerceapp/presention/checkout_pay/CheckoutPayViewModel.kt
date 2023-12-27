package com.ikpydev.ecommerceapp.presention.checkout_pay

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikpydev.ecommerceapp.domain.module.Card
import com.ikpydev.ecommerceapp.domain.module.UserInfo
import com.ikpydev.ecommerceapp.domain.repo.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutPayViewModel @Inject constructor(private val orderRepository: OrderRepository, ) :
    ViewModel() {

        val cards = MutableLiveData<List<Card>>()

    init {
        getCards()
    }
    fun getCards()=viewModelScope.launch{
        orderRepository.getCard().collect {

            cards.postValue(it)
        }
    }

    fun createOrder(promo:String?,userInfo: UserInfo,card: Card)= viewModelScope.launch(Dispatchers.Default) {
        try {
            orderRepository.createOrder(promo, userInfo, card)
        }catch (e:Exception){
        }
    }


}