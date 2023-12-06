package com.ikpydev.ecommerceapp.presention.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ikpydev.ecommerceapp.domain.module.Order
import com.ikpydev.ecommerceapp.domain.module.Status
import com.ikpydev.ecommerceapp.domain.repo.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    val loading = MutableLiveData(false)
    val empty = MutableLiveData(false)
    val error = MutableLiveData(false)
    val orders = MutableLiveData<PagingData<Order>?>()
    var mStatus: Status = Status.all

    init {
        getOrders()
    }

    fun getOrders() = viewModelScope.launch {
        loading.postValue(true)
        orders.postValue(null)
        error.postValue(false)
        orderRepository.getOrders(mStatus).cachedIn(viewModelScope).collectLatest {
            orders.postValue(it)
        }
    }

    fun setLoadStates(states: CombinedLoadStates, count: Int) {
        val loading = states.source.append is LoadState.Loading
        val error = states.source.refresh is LoadState.Error
        val empty = loading.not() && error.not() && count == 0
        this.loading.postValue(loading)
        this.empty.postValue(empty)
        this.error.postValue(error)
    }

    fun setStatus(status: Status) {
        mStatus = status
        getOrders()
    }
}