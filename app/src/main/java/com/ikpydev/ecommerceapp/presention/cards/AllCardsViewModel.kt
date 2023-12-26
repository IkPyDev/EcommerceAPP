package com.ikpydev.ecommerceapp.presention.cards

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikpydev.ecommerceapp.domain.module.Card
import com.ikpydev.ecommerceapp.domain.repo.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllCardsViewModel @Inject constructor(private val orderRepository: OrderRepository) :
    ViewModel() {

    val cards = MutableLiveData<List<Card>>()

    val loading = MutableLiveData(false)

    init {
        getCards()
    }

    private fun getCards() = viewModelScope.launch(Dispatchers.Default) {

        try {
            loading.postValue(true)
            orderRepository.getCard().collectLatest {
                cards.postValue(it)
                loading.postValue(false)
            }
        } catch (e: Exception) {
            loading.postValue(false)

        } finally {
            loading.postValue(false)
        }
    }

    fun clearCards() = viewModelScope.launch {
        orderRepository.clearCards()
    }
}