package com.ikpydev.ecommerceapp.presention.card

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikpydev.ecommerceapp.data.api.order.dto.Billing
import com.ikpydev.ecommerceapp.domain.module.Cart
import com.ikpydev.ecommerceapp.domain.repo.OrderRepository
import com.ikpydev.ecommerceapp.domain.repo.ProductRepository
import com.ikpydev.ecommerceapp.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    val carts = MutableLiveData<List<Cart>>()
    val billing_loading = MutableLiveData(false)
    val loading = MutableLiveData(false)
    val billing = MutableLiveData<Billing>()
    val evets = SingleLiveEvent<Event>()


    init {
        getCarts()
        getBilling()
    }

    private fun getCarts() = viewModelScope.launch {
        productRepository.getCart().collectLatest {
            carts.postValue(it)
        }
    }

    fun set(cart: Cart) = viewModelScope.launch {
        productRepository.setCart(cart)
    }

    fun clear() = viewModelScope.launch {
        productRepository.clearCart()
    }


    private var job: Job? = null
    fun getBilling(promo: String? = null) {
        job?.cancel()
        job = viewModelScope.launch {
            billing_loading.postValue(true)
            orderRepository.getBilling(promo).catch {
                evets.postValue(Event.BillingError)
                billing_loading.postValue(false)
            }.collectLatest {
                billing_loading.postValue(false)
                billing.postValue(it)
            }
        }

    }

    fun createOrder(promo: String? = null) = viewModelScope.launch {
        loading.postValue(true)
        try {
            orderRepository.createOrder(promo)
            evets.postValue(Event.OrderCrate)
        } catch (e: Exception) {
            evets.postValue(Event.OrderError)
        } finally {
            loading.postValue(false)
        }
    }

    sealed class Event {
        object BillingError : Event()
        object OrderError : Event()
        object OrderCrate : Event()
    }


}